package com.parkrangers.parkquest_backend.controller;

import com.parkrangers.parkquest_backend.model.response.Park;
import com.parkrangers.parkquest_backend.model.User;
import com.parkrangers.parkquest_backend.model.response.Subscription;
import com.parkrangers.parkquest_backend.repository.SubscriptionRepository;
import com.parkrangers.parkquest_backend.repository.UserRepository;
import com.parkrangers.parkquest_backend.repository.ParkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParkRepository parkRepository;

    // Create a new subscription using query parameters
    @PostMapping("/subscribe")
    public ResponseEntity<String> createSubscription(@RequestParam Long userId, @RequestParam String parkCode) {
        // Fetch the user and park using the provided userId and parkCode
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Park> parkOptional = parkRepository.findByParkCode(parkCode);

        if (userOptional.isEmpty() || parkOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid user or park information.");
        }

        // Create and save the subscription
        Subscription subscription = new Subscription();
        subscription.setUser(userOptional.get());
        subscription.setPark(parkOptional.get());

        subscriptionRepository.save(subscription);

        return ResponseEntity.ok("Subscription successful!");
    }

    // Delete a subscription using query parameters
    @DeleteMapping
    public ResponseEntity<String> deleteSubscription(@RequestParam Long userId, @RequestParam String parkCode) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Park> parkOptional = parkRepository.findByParkCode(parkCode);

        if (userOptional.isEmpty() || parkOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid user or park information.");
        }

        Optional<Subscription> subscriptionOptional = subscriptionRepository
                .findByUserAndPark(userOptional.get(), parkOptional.get());

        if (subscriptionOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        subscriptionRepository.delete(subscriptionOptional.get());
        return ResponseEntity.ok("Subscription removed.");
    }

    // Get all subscriptions for a user
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserSubscriptions(@PathVariable Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        return ResponseEntity.ok(subscriptionRepository.findByUser(userOptional.get()));
    }
}