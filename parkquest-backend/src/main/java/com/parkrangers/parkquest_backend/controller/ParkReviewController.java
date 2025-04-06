package com.parkrangers.parkquest_backend.controller;

import com.parkrangers.parkquest_backend.model.ParkReview;
import com.parkrangers.parkquest_backend.service.ParkReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/park-reviews")
public class ParkReviewController {

    @Autowired
    private ParkReviewService parkreviewService;

    // Get all park reviews for a specific park
    @GetMapping("/park/{parkId}")
    public List<ParkReview> getReviewsForPark(@PathVariable Long parkId) {
        return parkreviewService.getReviewsForPark(parkId);
    }

    // Create a new park review for a specific park
    @PostMapping("/{userId}/park/{parkId}")
    public ParkReview createReview(@PathVariable Long userId, @PathVariable Long parkId, @RequestBody ParkReview review) {
        return parkreviewService.createReview(userId, parkId, review);
    }

    // Edit an existing park review (only the review owner can edit it)
    @PutMapping("/{userId}/{parkId}/{parkReviewId}")
    public ParkReview editReview(@PathVariable Long userId, @PathVariable Long parkId, @PathVariable Long parkReviewId, @RequestBody ParkReview newReviewData) {
        return parkreviewService.editReview(userId, parkReviewId, parkId, newReviewData);
    }

}
