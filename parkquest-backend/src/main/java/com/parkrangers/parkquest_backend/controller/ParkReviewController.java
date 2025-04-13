package com.parkrangers.parkquest_backend.controller;

import com.parkrangers.parkquest_backend.model.ParkReview;
import com.parkrangers.parkquest_backend.model.request.ParkReviewRequest;
import com.parkrangers.parkquest_backend.service.ParkReviewService;
import com.parkrangers.parkquest_backend.service.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/park-reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ParkReviewController {

    @Autowired
    private ParkReviewService parkreviewService;
    @Autowired
    private ParkService parkService;

    // Get all park reviews for a specific park
    @GetMapping("/{parkCode}")
    public List<ParkReview> getReviewsForPark(@PathVariable String parkCode) {
        return parkreviewService.getReviewsForPark(parkCode);
    }

    // Create a new park review for a specific park
    @PostMapping
    public ResponseEntity<ParkReview> addReview(@RequestBody ParkReviewRequest request) {
        ParkReview review = parkreviewService.createReview(request.getUserId(), request.getParkCode(), request.getContent(), request.getRating());
        return ResponseEntity.ok(review);
    }

    // Edit an existing park review (only the review owner can edit it)
    @PutMapping("/{reviewId}")
    public ResponseEntity<ParkReview> editReview(
            @PathVariable Long reviewId,
            @RequestBody ParkReviewRequest request) {
        ParkReview updatedReview = parkreviewService.editReview(request.getUserId(), request.getParkCode(), request.getContent(), reviewId,  request.getRating());
        if (updatedReview != null) {
            return ResponseEntity.ok(updatedReview);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a park review (only the review owner can delete it)
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId, @RequestParam Long userId) {
        boolean deleted = parkreviewService.deleteReview(reviewId, userId);
        if (deleted) {
            return ResponseEntity.noContent().build();  // Successfully deleted
        } else {
            return ResponseEntity.notFound().build();   // Review not found or user does not own the review
        }
    }
}
