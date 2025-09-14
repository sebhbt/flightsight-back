package com.sbthbt.flightsight_back.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sbthbt.flightsight_back.db.entity.ReviewEntity;
import com.sbthbt.flightsight_back.db.repository.ReviewRepository;
import com.sbthbt.flightsight_back.specification.ReviewSearchCriteria;
import com.sbthbt.flightsight_back.specification.ReviewSpecification;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public ReviewEntity createReview(ReviewEntity review) {
        return reviewRepository.save(review);
    }

    public Optional<ReviewEntity> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public List<ReviewEntity> getReviewsByFlightId(Long flightId) {
        return reviewRepository.findByFlightId(flightId);
    }

    public ReviewEntity updateReview(Long id, ReviewEntity reviewDetails) {
        return reviewRepository.findById(id)
                .map(review -> {
                    review.setTitle(reviewDetails.getTitle());
                    review.setComment(reviewDetails.getComment());
                    review.setRating(reviewDetails.getRating());
                    return reviewRepository.save(review);
                })
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public Page<ReviewEntity> searchReviews(ReviewSearchCriteria criteria, Pageable pageable) {
        Specification<ReviewEntity> spec = ReviewSpecification.withCriteria(criteria);
        return reviewRepository.findAll(spec, pageable);
    }
}
