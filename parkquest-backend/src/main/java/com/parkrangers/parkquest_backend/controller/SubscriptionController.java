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
@CrossOrigin(origins = "http://localhost:5173")

public class SubscriptionController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParkRepository parkRepository;

    // Create a new subscription using query parameters
    @PostMapping("/subscribe")
    public ResponseEntity<?> createSubscription(@RequestParam Long userId, @RequestParam String parkCode) {
        System.out.println("=== Subscription Endpoint Hit ===");
        System.out.println("Received userId: " + userId);
        System.out.println("Received parkCode: " + parkCode);
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid user.");
        }

        // Check if the park already exists in the favorite_park table
        Optional<Park> parkOptional = parkRepository.findByParkCode(parkCode);

        // If the park doesn't exist, create a new park and save it
        Park park = parkOptional.orElseGet(() -> {
            Park newPark = new Park();
            newPark.setParkCode(parkCode);
            newPark.setFullName("Default Name"); // You can enhance this by fetching the actual park name via an API
            return parkRepository.save(newPark);  // Save the new park to the favorite_park table
        });

        // Create a new subscription and associate it with the user and the park
        Subscription subscription = new Subscription();
        subscription.setUser(userOptional.get());
        subscription.setPark(park);  // Link the subscription to the park
        subscriptionRepository.save(subscription);  // Save the subscription to the subscription table

        // Return a success message
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
    @GetMapping("/user")
    public ResponseEntity<?> getUserSubscriptions(@RequestParam Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        return ResponseEntity.ok(subscriptionRepository.findByUser(userOptional.get()));
    }
}