package com.sbthbt.flightsight_back.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sbthbt.flightsight_back.db.entity.FlightEntity;
import com.sbthbt.flightsight_back.db.repository.FlightRepository;
import com.sbthbt.flightsight_back.specification.FlightSearchCriteria;
import com.sbthbt.flightsight_back.specification.FlightSpecification;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public Optional<FlightEntity> getFlightById(Long id) {
        return flightRepository.findById(id);
    }

    public Page<FlightEntity> searchFlights(FlightSearchCriteria criteria, Pageable pageable) {
        if (criteria == null || isCriteriaEmpty(criteria)) {
            return flightRepository.findAll(pageable);
        }

        Specification<FlightEntity> spec = FlightSpecification.withCriteria(criteria);
        return flightRepository.findAll(spec, pageable);
    }

    private boolean isCriteriaEmpty(FlightSearchCriteria criteria) {
        return (criteria.getFlightNumber() == null ||
                criteria.getFlightNumber().isEmpty()) &&
                (criteria.getAirline() == null ||
                        criteria.getAirline().isEmpty())
                &&
                (criteria.getDepartureAirport() == null ||
                        criteria.getDepartureAirport().isEmpty())
                &&
                (criteria.getArrivalAirport() == null ||
                        criteria.getArrivalAirport().isEmpty())
                &&
                criteria.getDepartureFrom() == null &&
                criteria.getDepartureTo() == null;
    }
}
