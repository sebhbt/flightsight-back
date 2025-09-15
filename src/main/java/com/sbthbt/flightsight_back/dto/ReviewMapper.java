package com.sbthbt.flightsight_back.dto;

import com.sbthbt.flightsight_back.db.entity.ReviewEntity;

public class ReviewMapper {

    public static ReviewDto toDTO(ReviewEntity review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getReviewId());
        dto.setCustomerId(review.getCustomerId());
        dto.setFlightId(review.getFlight().getFlightId());
        dto.setAirlineName(review.getFlight().getAirline());
        dto.setTitle(review.getTitle());
        dto.setComment(review.getComment());
        dto.setRating(review.getRating());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setUpdatedAt(review.getUpdatedAt());
        return dto;
    }
}
