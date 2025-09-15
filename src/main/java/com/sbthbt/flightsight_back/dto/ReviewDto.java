package com.sbthbt.flightsight_back.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for creating or updating a review")
public class ReviewDto {

    @Schema(description = "ID of the flight being reviewed", example = "1", required = true)
    private Long flightId;

    @Schema(description = "Review title", example = "Great flight!", required = true)
    private String title;

    @Schema(description = "Review content", example = "The flight was comfortable and on time.", required = true)
    private String comment;

    @Schema(description = "Rating from 1 to 5", example = "5", required = true)
    private Integer rating;

    // Getters and setters
    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
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
}
