package com.sbthbt.flightsight_back.db.entity;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "flights")
@Schema(description = "Represents a flight")
public class FlightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique ID of the flight", example = "1")
    private Long flightId;

    @Column(name = "flight_number", nullable = false)
    @Schema(description = "Flight number", example = "AFR123")
    private String flightNumber;

    @Column(name = "airline", nullable = false)
    @Schema(description = "Airline company", example = "Air France")
    private String airline;

    @Column(name = "departure_datetime", nullable = false)
    @Schema(description = "Departure date and time (UTC)", example = "2025-09-10T12:00:00")
    private LocalDateTime departureDatetime;

    @Column(name = "arrival_datetime", nullable = false)
    @Schema(description = "Arrival date and time (UTC)", example = "2025-09-10T14:30:00")
    private LocalDateTime arrivalDatetime;

    @Column(name = "departure_airport", nullable = false)
    @Schema(description = "Departure airport code", example = "CDG")
    private String departureAirport;

    @Column(name = "arrival_airport", nullable = false)
    @Schema(description = "Arrival airport code", example = "JFK")
    private String arrivalAirport;

    @Column(name = "created_at", updatable = false)
    @Schema(description = "Creation timestamp of the record", example = "2025-09-13T10:00:00")
    private LocalDateTime createdAt;

    // Constructors, getters, and setters
    public FlightEntity() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters et setters pour chaque champ
    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

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

    public LocalDateTime getDepartureDatetime() {
        return departureDatetime;
    }

    public void setDepartureDatetime(LocalDateTime departureDatetime) {
        this.departureDatetime = departureDatetime;
    }

    public LocalDateTime getArrivalDatetime() {
        return arrivalDatetime;
    }

    public void setArrivalDatetime(LocalDateTime arrivalDatetime) {
        this.arrivalDatetime = arrivalDatetime;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
