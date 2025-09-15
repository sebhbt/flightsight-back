package com.sbthbt.flightsight_back.specification;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Criteria for searching flights")
public class FlightSearchCriteria {

    @Schema(description = "Flight number", example = "AFR123")
    private String flightNumber;

    @Schema(description = "Airline name", example = "Air France")
    private String airline;

    @Schema(description = "Departure airport code", example = "CDG")
    private String departureAirport;

    @Schema(description = "Arrival airport code", example = "JFK")
    private String arrivalAirport;

    @Schema(description = "Start date for departure (UTC)", example = "2025-09-01T00:00:00")
    private LocalDateTime departureFrom;

    @Schema(description = "End date for departure (UTC)", example = "2025-09-30T23:59:59")
    private LocalDateTime departureTo;

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public LocalDateTime getDepartureFrom() {
        return departureFrom;
    }

    public void setDepartureFrom(LocalDateTime departureFrom) {
        this.departureFrom = departureFrom;
    }

    public LocalDateTime getDepartureTo() {
        return departureTo;
    }

    public void setDepartureTo(LocalDateTime departureTo) {
        this.departureTo = departureTo;
    }
}
