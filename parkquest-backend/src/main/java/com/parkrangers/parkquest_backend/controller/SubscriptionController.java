package com.parkrangers.parkquest_backend.controller;

import com.parkrangers.parkquest_backend.model.request.SubscriptionRequest;
import com.parkrangers.parkquest_backend.model.response.Subscription;
import com.parkrangers.parkquest_backend.repository.UserRepository;
import com.parkrangers.parkquest_backend.service.EventService;
import com.parkrangers.parkquest_backend.service.NotificationService;
import com.parkrangers.parkquest_backend.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/subscriptions")
@CrossOrigin(origins = "http://localhost:5173")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventService eventService;


   @PostMapping("/subscribe")
   public ResponseEntity<?> addSubscription(@RequestBody SubscriptionRequest request) {
        Subscription subscription = subscriptionService.createSubscription(request.getUserId(), request.getParkCode());

        if (subscription != null) {
            return ResponseEntity.ok(subscription);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("You’re already subscribed to this park.");
        }
    }

    @GetMapping("/check")
    public ResponseEntity<Map<String, Boolean>> checkSubscription(
            @RequestParam Long userId,
            @RequestParam String parkCode) {
        boolean isSubscribed = subscriptionService.isUserSubscribedToPark(userId, parkCode);
        Map<String, Boolean> response = new HashMap<>();
        response.put("subscribed", isSubscribed);
        return ResponseEntity.ok(response);
    }
}