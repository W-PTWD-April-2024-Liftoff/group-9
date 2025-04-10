package com.parkrangers.parkquest_backend.repository;

import com.parkrangers.parkquest_backend.model.response.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByUserId(Long userId);
    List<Trip> findByParkCode(String parkCode);
    void deleteByParkCode(String parkCode);

}