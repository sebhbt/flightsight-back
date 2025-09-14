package com.sbthbt.flightsight_back.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbthbt.flightsight_back.db.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    List<CustomerEntity> findByLastNameAndFirstName(String lastName, String firstName);
}
