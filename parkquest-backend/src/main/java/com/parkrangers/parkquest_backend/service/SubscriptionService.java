package com.parkrangers.parkquest_backend.service;

import com.parkrangers.parkquest_backend.model.response.Event;
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

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EventService eventService;


    public Subscription createSubscription(Long userId, String parkCode) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            System.out.println(" User not found with ID: " + userId);
            return null;
        }

        User user = userOptional.get();

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setParkCode(parkCode);

        subscriptionRepository.save(subscription);
        List<Event> events = eventService.fetchEventsByParkCode(parkCode);
        notificationService.sendSubscriptionEmail(user.getEmail(), parkCode, events);

        System.out.println("Email sent to " + user.getEmail() + " for park " + parkCode);

        return subscription;
    }


    public boolean isUserSubscribedToPark(Long userId, String parkCode) {
        return subscriptionRepository.existsByUserUserIdAndParkCode(userId, parkCode);
    }

}
