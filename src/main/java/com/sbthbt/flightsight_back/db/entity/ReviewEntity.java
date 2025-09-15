package com.sbthbt.flightsight_back.db.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.sbthbt.flightsight_back.db.ReviewStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
@Schema(description = "Represents a review for a flight")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique ID of the review", example = "1")
    private Long reviewId;

    @Column(name = "customer_id", nullable = false)
    @Schema(description = "ID of the customer who wrote the review", example = "1")
    private Long customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    @Schema(description = "ID of the flight being reviewed", example = "1")
    private FlightEntity flight;

    @Column(nullable = false)
    @Schema(description = "Review title", example = "Great flight!")
    private String title;

    @Column(nullable = false, length = 1000)
    @Schema(description = "Review comment", example = "The flight was comfortable and on time.")
    private String comment;

    @Column(nullable = false)
    @Schema(description = "Rating from 1 to 10", example = "5")
    private Integer rating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Schema(description = "Status of the review", example = "processed")
    private ReviewStatus status;

    @Column(nullable = false, updatable = false)
    @Schema(description = "Creation timestamp of the review", example = "2025-09-13T10:00:00")
    private LocalDateTime createdAt;

    @Column
    @Schema(description = "Last update timestamp of the review", example = "2025-09-13T11:00:00")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "List of responses to this review")
    private List<ResponseReviewEntity> responses;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        this.status = ReviewStatus.UNPROCESSED;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long id) {
        this.reviewId = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public FlightEntity getFlight() {
        return flight;
    }

    public void setFlight(FlightEntity flight) {
        this.flight = flight;
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

    public ReviewStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
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
