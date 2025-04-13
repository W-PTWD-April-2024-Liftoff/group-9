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

    /**
     * Fetch all subscriptions for a specific user.
     * @param userId The ID of the user.
     * @return A list of park codes that the user is subscribed to.
     */
    public List<String> getSubscriptionsForUser(Long userId) {
        return userRepository.findById(userId)
                .map(user -> subscriptionRepository.findByUser(user).stream()
                        .map(Subscription::getParkCode) // Retrieve the park codes only
                        .toList())
                .orElse(null); // Return null if no subscriptions exist for the user
    }

    /**
     * Create a new subscription for a user to a specific park.
     * @param userId The ID of the user.
     * @param parkCode The code of the park.
     * @return The created subscription or null if the subscription already exists.
     */
    public Subscription createSubscription(Long userId, String parkCode) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            System.out.println("❌ User not found with ID: " + userId);
            return null; // Return null if the user is not found
        }


        User user = userOptional.get();

        // Check if the user is already subscribed to the park
        Optional<Subscription> existing = subscriptionRepository.findByUserAndParkCode(user, parkCode);
        if (existing.isPresent()) {
            System.out.println("❌ Subscription already exists for user: " + userId + " to park: " + parkCode);
            return null; // Return null if the user is already subscribed to the park
        }

        // Create a new subscription
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setParkCode(parkCode);

        // Save the subscription to the database
        return subscriptionRepository.save(subscription);
    }

    /**
     * Delete a subscription for a user from a specific park.
     * @param userId The ID of the user.
     * @param parkCode The code of the park.
     * @return true if the subscription was deleted, false otherwise.
     */
    public boolean deleteSubscription(Long userId, String parkCode) {
        return userRepository.findById(userId).map(user -> {
            Optional<Subscription> subscription = subscriptionRepository.findByUserAndParkCode(user, parkCode);
            if (subscription.isPresent()) {
                // Delete the found subscription
                subscriptionRepository.delete(subscription.get());
                return true;
            }
            return false; // Return false if no subscription exists
        }).orElse(false); // Return false if the user does not exist
    }
}