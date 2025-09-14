package com.sbthbt.flightsight_back.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.sbthbt.flightsight_back.db.entity.FlightEntity;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long>, JpaSpecificationExecutor<FlightEntity> {
    // Page<FlightEntity> findAll(Pageable pageable);
}
