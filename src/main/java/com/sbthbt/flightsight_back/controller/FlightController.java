package com.sbthbt.flightsight_back.controller;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbthbt.flightsight_back.db.entity.FlightEntity;
import com.sbthbt.flightsight_back.db.repository.FlightRepository;
import com.sbthbt.flightsight_back.dto.OpenSkyFlightDto;
import com.sbthbt.flightsight_back.services.FlightService;
import com.sbthbt.flightsight_back.services.OpenSkyApiService;
import com.sbthbt.flightsight_back.specification.FlightSearchCriteria;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@Tag(name = "Flights", description = "API for managing flights")
public class FlightController {

    @Autowired
    private OpenSkyApiService openSkyApiService;

    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightRepository flightRepository;

    @GetMapping("/fetch-flights")
    public String fetchFlights() {
        long begin = getBeginTimestamp();
        long end = getEndTimestamp();
        Mono<String> stringResponse = openSkyApiService.fetchFlightsFromLast30Days(begin, end).flatMap(response -> {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                List<OpenSkyFlightDto> flights = objectMapper.readValue(response,
                        new TypeReference<List<OpenSkyFlightDto>>() {
                        });

                // Filtrer les vols incomplets
                List<OpenSkyFlightDto> validFlights = flights.stream()
                        .filter(this::isOpenSkyFlightDtoValid)
                        .collect(Collectors.toList());

                // Convertir en entités Flight et sauvegarder
                List<FlightEntity> flightEntities = validFlights.stream()
                        .map(this::toFlightEntity)
                        .toList();
                flightRepository.saveAll(flightEntities);
                return Mono.just(String.format("Vols sauvegardés avec succès ! %d vols valides sur %d.",
                        flightEntities.size(), flights.size()));
            } catch (Exception e) {
                return Mono.just("Erreur lors du parsing : " + e.getMessage());
            }
        });
        ;
        // Ici, vous pouvez parser la réponse et sauvegarder en base de données
        return stringResponse.block();
    }

    @GetMapping("/flights/{id}")
    @Operation(summary = "Get a flight by its ID", description = "Returns the details of a specific flight based on its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight found"),
            @ApiResponse(responseCode = "404", description = "Flight not found")
    })
    public ResponseEntity<FlightEntity> getFlightById(
            @Parameter(description = "ID of the flight to retrieve", example = "1") @PathVariable Long id) {
        Optional<FlightEntity> flight = flightService.getFlightById(id);
        return flight.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/flights/search")
    @Operation(summary = "Search flights with pagination and sorting", description = """
                Returns a paginated list of flights matching the search criteria.
                If no criteria are provided, returns the 20 most recent flights by departure date.
            """)
    @ApiResponse(responseCode = "200", description = "List of flights matching the criteria or the 20 most recent flights")
    public Page<FlightEntity> searchFlights(
            @Parameter(description = """
                        Search criteria for flights.
                        If empty, the 20 most recent flights by departure date are returned.
                    """) @ModelAttribute FlightSearchCriteria criteria,
            @ParameterObject @PageableDefault(size = 20, sort = "departureDatetime", direction = Sort.Direction.DESC) Pageable pageable) {
        return flightService.searchFlights(criteria, pageable);
    }

    private FlightEntity toFlightEntity(OpenSkyFlightDto dto) {
        FlightEntity flight = new FlightEntity();
        flight.setFlightNumber(dto.getCallsign().trim());
        flight.setAirline(extractAirlineFromCallsign(dto.getCallsign())); // Exemple : "AFR123" -> "Air France"
        flight.setDepartureDatetime(
                Instant.ofEpochSecond(dto.getFirstSeen()).atZone(ZoneId.of("UTC")).toLocalDateTime());
        flight.setArrivalDatetime(
                Instant.ofEpochSecond(dto.getLastSeen()).atZone(ZoneId.of("UTC")).toLocalDateTime());
        flight.setDepartureAirport(dto.getEstDepartureAirport());
        flight.setArrivalAirport(dto.getEstArrivalAirport());
        return flight;
    }

    private boolean isOpenSkyFlightDtoValid(OpenSkyFlightDto dto) {
        return dto != null
                && dto.getIcao24() != null
                && dto.getCallsign() != null
                && dto.getEstDepartureAirport() != null
                && dto.getEstArrivalAirport() != null
                && dto.getFirstSeen() != 0
                && dto.getLastSeen() != 0;
    }

    private String extractAirlineFromCallsign(String callsign) {
        // Logique pour extraire la compagnie aérienne depuis le callsign
        // Exemple : "AFR123" -> "Air France"
        return callsign.substring(0, 3); // Simplifié pour l'exemple
    }

    private long getBeginTimestamp() {
        return Instant.now().minus(2, ChronoUnit.HOURS).getEpochSecond();
    }

    private long getEndTimestamp() {
        return Instant.now().minus(61, ChronoUnit.MINUTES).getEpochSecond();
    }
}