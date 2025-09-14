package com.sbthbt.flightsight_back.specification;

import org.springframework.data.jpa.domain.Specification;

import com.sbthbt.flightsight_back.db.entity.FlightEntity;

import jakarta.persistence.criteria.Predicate;

public class FlightSpecification {

    public static Specification<FlightEntity> withCriteria(FlightSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (criteria.getFlightNumber() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("flightNumber"), criteria.getFlightNumber()));
            }

            if (criteria.getAirline() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("airline"), criteria.getAirline()));
            }

            if (criteria.getDepartureAirport() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("departureAirport"), criteria.getDepartureAirport()));
            }

            if (criteria.getArrivalAirport() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("arrivalAirport"), criteria.getArrivalAirport()));
            }

            if (criteria.getDepartureFrom() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("departureDatetime"),
                                criteria.getDepartureFrom()));
            }

            if (criteria.getDepartureTo() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.lessThanOrEqualTo(root.get("departureDatetime"), criteria.getDepartureTo()));
            }

            return predicate;
        };
    }
}
