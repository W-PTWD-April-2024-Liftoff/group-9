package com.parkrangers.parkquest_backend.repositories;

import com.parkrangers.parkquest_backend.models.TripPlan;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripPlanRepository extends CrudRepository <TripPlan, Integer> {
}


