package com.sbthbt.flightsight_back.controller;

import java.util.Optional;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbthbt.flightsight_back.db.entity.ReviewEntity;
import com.sbthbt.flightsight_back.db.repository.FlightRepository;
import com.sbthbt.flightsight_back.dto.ReviewDto;
import com.sbthbt.flightsight_back.dto.ReviewMapper;
import com.sbthbt.flightsight_back.services.ReviewService;
import com.sbthbt.flightsight_back.specification.ReviewSearchCriteria;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Reviews", description = "API for managing flight reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private FlightRepository flightRepository;

    @PostMapping
    @Operation(summary = "Create a new review", description = "Creates a new review for a flight")
    @ApiResponse(responseCode = "201", description = "Review created successfully")
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) {
        ReviewEntity review = new ReviewEntity();
        review.setCustomerId((long) 1);
        review.setFlight(flightRepository.getReferenceById(reviewDto.getFlightId()));
        review.setTitle(reviewDto.getTitle());
        review.setComment(reviewDto.getComment());
        review.setRating(reviewDto.getRating());
        ReviewEntity createdReview = reviewService.createReview(review);
        return ResponseEntity.status(201).body(ReviewMapper.toDTO(createdReview));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a review by ID", description = "Returns a review by its ID")
    @ApiResponse(responseCode = "200", description = "Review found")
    @ApiResponse(responseCode = "404", description = "Review not found")
    public ResponseEntity<ReviewDto> getReviewById(
            @Parameter(description = "ID of the review") @PathVariable Long id) {
        Optional<ReviewEntity> review = reviewService.getReviewById(id);
        return review.map(ReviewMapper::toDTO).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a review", description = "Updates an existing review")
    @ApiResponse(responseCode = "200", description = "Review updated successfully")
    @ApiResponse(responseCode = "404", description = "Review not found")
    public ResponseEntity<ReviewDto> updateReview(
            @Parameter(description = "ID of the review to update") @PathVariable Long id,
            @RequestBody ReviewDto reviewDto) {
        ReviewEntity reviewDetails = new ReviewEntity();
        reviewDetails.setTitle(reviewDto.getTitle());
        reviewDetails.setComment(reviewDto.getComment());
        reviewDetails.setRating(reviewDto.getRating());
        ReviewEntity updatedReview = reviewService.updateReview(id, reviewDetails);
        return ResponseEntity.ok(ReviewMapper.toDTO(updatedReview));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a review", description = "Deletes a review by its ID")
    @ApiResponse(responseCode = "204", description = "Review deleted successfully")
    public ResponseEntity<Void> deleteReview(
            @Parameter(description = "ID of the review to delete") @PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search reviews with pagination and filtering", description = """
                Returns a paginated list of reviews matching the search criteria.
                If no criteria are provided, returns all reviews sorted by creation date (descending).
                Default pagination: 20 items per page.
            """)
    @ApiResponse(responseCode = "200", description = "Paginated list of reviews matching the criteria")
    public Page<ReviewDto> searchReviews(
            @Parameter(description = "Search criteria for reviews (optional)") @ModelAttribute ReviewSearchCriteria criteria,
            @ParameterObject @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, size = 20) Pageable pageable) {
        Page<ReviewEntity> reviews = reviewService.searchReviews(criteria, pageable);
        return reviews.map(ReviewMapper::toDTO);
    }
}
