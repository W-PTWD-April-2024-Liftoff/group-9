package com.parkrangers.parkquest_backend.repository;

import com.parkrangers.parkquest_backend.model.User;
import com.parkrangers.parkquest_backend.model.response.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUser(User user);
    Optional<Subscription> findByUserAndParkCode(User user, String parkCode);
}