package com.parkrangers.parkquest_backend.service;

import com.parkrangers.parkquest_backend.model.response.Subscription;
import com.parkrangers.parkquest_backend.model.User;
import com.parkrangers.parkquest_backend.model.response.Park;
import com.parkrangers.parkquest_backend.repository.SubscriptionRepository;
import com.parkrangers.parkquest_backend.repository.UserRepository;
import com.parkrangers.parkquest_backend.repository.ParkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParkRepository parkRepository;

    public Subscription createSubscription(Long userId, String parkCode) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Park> parkOptional = parkRepository.findByParkCode(parkCode);

        if (userOptional.isEmpty() || parkOptional.isEmpty()) {
            return null; // Return null or throw an exception if necessary
        }

        Subscription subscription = new Subscription(userOptional.get(), parkOptional.get());
        return subscriptionRepository.save(subscription);
    }

    public void deleteSubscription(Long userId, String parkCode) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Park> parkOptional = parkRepository.findByParkCode(parkCode);

        if (userOptional.isPresent() && parkOptional.isPresent()) {
            Optional<Subscription> subscriptionOptional = subscriptionRepository
                    .findByUserAndPark(userOptional.get(), parkOptional.get());

            subscriptionOptional.ifPresent(subscriptionRepository::delete);
        }
    }
}
