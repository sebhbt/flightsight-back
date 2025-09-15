package com.sbthbt.flightsight_back.specification;

import org.springframework.data.jpa.domain.Specification;

import com.sbthbt.flightsight_back.db.entity.FlightEntity;
import com.sbthbt.flightsight_back.db.entity.ResponseReviewEntity;
import com.sbthbt.flightsight_back.db.entity.ReviewEntity;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

public class ReviewSpecification {

    public static Specification<ReviewEntity> withCriteria(ReviewSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {

            if (criteria == null || isAllCriteriaNull(criteria)) {
                return criteriaBuilder.conjunction();
            }

            Predicate predicate = criteriaBuilder.conjunction();

            if (criteria.getFlightId() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("flight").get("flightId"), criteria.getFlightId()));
            }

            if (criteria.getAirlineName() != null) {
                Join<ReviewEntity, FlightEntity> flightJoin = root.join("flight");
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(
                                criteriaBuilder.lower(flightJoin.get("airline")),
                                "%" + criteria.getAirlineName().toLowerCase() + "%"));
            }

            if (criteria.getStatus() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("status"), criteria.getStatus()));
            }

            if (criteria.getCreatedFrom() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), criteria.getCreatedFrom()));
            }

            if (criteria.getCreatedTo() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), criteria.getCreatedTo()));
            }

            if (criteria.getHasResponse() != null) {
                if (criteria.getHasResponse()) {
                    Subquery<Long> subquery = query.subquery(Long.class);
                    Root<ResponseReviewEntity> responseRoot = subquery.from(ResponseReviewEntity.class);
                    subquery.select(responseRoot.get("review").get("id"))
                            .where(criteriaBuilder.equal(responseRoot.get("review").get("id"), root.get("id")));
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.exists(subquery));
                } else {
                    Subquery<Long> subquery = query.subquery(Long.class);
                    Root<ResponseReviewEntity> responseRoot = subquery.from(ResponseReviewEntity.class);
                    subquery.select(responseRoot.get("review").get("id"))
                            .where(criteriaBuilder.equal(responseRoot.get("review").get("id"), root.get("id")));
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.not(criteriaBuilder.exists(subquery)));
                }
            }

            return predicate;
        };
    }

    private static boolean isAllCriteriaNull(ReviewSearchCriteria criteria) {
        return criteria.getFlightId() == null &&
                criteria.getAirlineName() == null &&
                criteria.getCreatedFrom() == null &&
                criteria.getCreatedTo() == null &&
                criteria.getHasResponse() == null;
    }
}