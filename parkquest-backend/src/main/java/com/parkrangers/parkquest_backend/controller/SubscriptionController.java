package com.parkrangers.parkquest_backend.controller;

import com.parkrangers.parkquest_backend.model.request.SubscriptionRequest;
import com.parkrangers.parkquest_backend.model.response.Subscription;
import com.parkrangers.parkquest_backend.repository.UserRepository;
import com.parkrangers.parkquest_backend.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
@CrossOrigin(origins = "http://localhost:3000")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private UserRepository userRepository;


    // Create a new subscription for a user to a park
    @PostMapping("/subscribe")
    public ResponseEntity<Subscription> addSubscription(@RequestBody SubscriptionRequest request) {
        System.out.println("Request received: userId=" + request.getUserId() + ", parkCode=" + request.getParkCode());

        Subscription subscription = subscriptionService.createSubscription(request.getUserId(), request.getParkCode());
        if (subscription != null) {
            return ResponseEntity.ok(subscription);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}