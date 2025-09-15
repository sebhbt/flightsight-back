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
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("flightNumber")),
                                "%" + criteria.getFlightNumber().toLowerCase() + "%"));
            }

            if (criteria.getAirline() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("airline")),
                                "%" + criteria.getAirline().toLowerCase() + "%"));
            }

            if (criteria.getDepartureAirport() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("departureAirport")),
                                "%" + criteria.getDepartureAirport().toLowerCase() + "%"));
            }

            if (criteria.getArrivalAirport() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("arrivalAirport")),
                                "%" + criteria.getArrivalAirport().toLowerCase() + "%"));
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
