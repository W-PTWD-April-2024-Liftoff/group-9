package com.parkrangers.parkquest_backend.repository;

import com.parkrangers.parkquest_backend.model.response.Park;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripPlanRepository extends JpaRepository<Park, Long> {
}
