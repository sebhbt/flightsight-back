package com.sbthbt.flightsight_back.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sbthbt.flightsight_back.db.entity.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>, JpaSpecificationExecutor<ReviewEntity> {
    List<ReviewEntity> findByFlightFlightId(Long flightId);
}
