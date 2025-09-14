package com.sbthbt.flightsight_back.specification;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Criteria for searching reviews")
public class ReviewSearchCriteria {

    @Schema(description = "ID of the flight", example = "1")
    private Long flightId;

    @Schema(description = "Name of the airline", example = "Air France")
    private String airlineName;

    @Schema(description = "Start date for review creation", example = "2025-09-01T00:00:00")
    private LocalDateTime createdFrom;

    @Schema(description = "End date for review creation", example = "2025-09-30T23:59:59")
    private LocalDateTime createdTo;

    @Schema(description = "Filter by reviews with at least one response", example = "true")
    private Boolean hasResponse;

    // Getters and setters
    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public LocalDateTime getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(LocalDateTime createdFrom) {
        this.createdFrom = createdFrom;
    }

    public LocalDateTime getCreatedTo() {
        return createdTo;
    }

    public void setCreatedTo(LocalDateTime createdTo) {
        this.createdTo = createdTo;
    }

    public Boolean getHasResponse() {
        return hasResponse;
    }

    public void setHasResponse(Boolean hasResponse) {
        this.hasResponse = hasResponse;
    }
}
