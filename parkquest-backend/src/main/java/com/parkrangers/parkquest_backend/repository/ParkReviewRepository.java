package com.parkrangers.parkquest_backend.repository;

import com.parkrangers.parkquest_backend.model.ParkReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkReviewRepository extends JpaRepository<ParkReview, Long> {
    List<ParkReview> findByParkId(Long parkId);
}