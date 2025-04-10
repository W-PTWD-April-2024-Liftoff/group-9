package com.parkrangers.parkquest_backend.repository;

import com.parkrangers.parkquest_backend.model.response.Subscription;
import com.parkrangers.parkquest_backend.model.response.Park;
import com.parkrangers.parkquest_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    // Find subscriptions by user
    List<Subscription> findByUser(User user);

    // Find subscription by user and park
    Optional<Subscription> findByUserAndPark(User user, Park park);
}
