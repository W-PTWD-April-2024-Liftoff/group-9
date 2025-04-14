package com.parkrangers.parkquest_backend.service;

import com.parkrangers.parkquest_backend.model.response.Subscription;
import com.parkrangers.parkquest_backend.model.User;
import com.parkrangers.parkquest_backend.repository.SubscriptionRepository;
import com.parkrangers.parkquest_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;


    public List<String> getSubscriptionsForUser(Long userId) {
        return userRepository.findById(userId)
                .map(user -> subscriptionRepository.findByUser(user).stream()
                        .map(Subscription::getParkCode) // Retrieve the park codes only
                        .toList())
                .orElse(null); // Return null if no subscriptions exist for the user
    }


    public Subscription createSubscription(Long userId, String parkCode) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            System.out.println(" User not found with ID: " + userId);
            return null;
        }

        User user = userOptional.get();

        // Check if the user is already subscribed to the park
        Optional<Subscription> existing = subscriptionRepository.findByUserAndParkCode(user, parkCode);
        if (existing.isPresent()) {
            System.out.println("Subscription already exists for user: " + userId + " to park: " + parkCode);
            return null; // Return null if the user is already subscribed to the park
        }

        // Create a new subscription
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setParkCode(parkCode);

        // Save the subscription to the database
        return subscriptionRepository.save(subscription);
    }


    public boolean deleteSubscription(Long userId, String parkCode) {
        return userRepository.findById(userId).map(user -> {
            Optional<Subscription> subscription = subscriptionRepository.findByUserAndParkCode(user, parkCode);
            if (subscription.isPresent()) {
                // Delete the found subscription
                subscriptionRepository.delete(subscription.get());
                return true;
            }
            return false;
        }).orElse(false);
    }
}
