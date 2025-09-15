package com.sbthbt.flightsight_back.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for creating or updating a review")
public class ReviewDto {

    @Schema(description = "Unique ID of the review", example = "1")
    private Long id;

    @Schema(description = "ID of the customer who wrote the review", example = "1")
    private Long customerId;

    @Schema(description = "ID of the flight being reviewed", example = "1")
    private Long flightId;

    @Schema(description = "Name of the airline", example = "AFR")
    private String airlineName;

    @Schema(description = "Review title", example = "Great flight!")
    private String title;

    @Schema(description = "Review content", example = "The flight was comfortable and on time.")
    private String comment;

    @Schema(description = "Rating from 1 to 10", example = "5")
    private Integer rating;

    @Schema(description = "Creation timestamp of the review", example = "2025-09-13T10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Last update timestamp of the review", example = "2025-09-13T11:00:00")
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String content) {
        this.comment = content;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
