package com.parkrangers.parkquest_backend.repository;
import com.parkrangers.parkquest_backend.model.response.Park;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkRepository extends JpaRepository<Park, Long> {
    List<Park> findByUserId(Long userId);
    Optional<Park> findByUserIdAndParkCode(Long userId, String parkCode);
    Optional<Park> findByParkCode(String parkCode);
}