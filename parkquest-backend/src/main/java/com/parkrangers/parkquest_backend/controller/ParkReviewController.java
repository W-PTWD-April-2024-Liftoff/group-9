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
@CrossOrigin(origins = "http://localhost:5173")
public class ParkReviewController {

    @Autowired
    private ParkReviewService parkReviewService;

    @Autowired
    private ParkService parkService;

    // Get all park reviews for a specific park
    @GetMapping("/{parkCode}")
    public ResponseEntity<List<ParkReview>> getReviewsForPark(@PathVariable String parkCode) {
        List<ParkReview> reviews = parkReviewService.getReviewsForPark(parkCode);
        if (reviews.isEmpty()) {
            return ResponseEntity.noContent().build(); // No reviews available
        }
        return ResponseEntity.ok(reviews); // Return reviews
    }

    // Create a new park review for a specific park
    @PostMapping
    public ResponseEntity<ParkReview> addReview(@RequestBody ParkReviewRequest request) {
        ParkReview review = parkReviewService.createReview(request.getUserId(), request.getParkCode(), request.getContent(), request.getRating());
        return ResponseEntity.ok(review); // Return created review
    }

    // Edit an existing park review (only the review owner can edit it)
    @PutMapping("/{reviewId}")
    public ResponseEntity<ParkReview> editReview(@PathVariable Long reviewId, @RequestBody ParkReviewRequest request) {
        ParkReview updatedReview = parkReviewService.editReview(request.getUserId(), request.getParkCode(), request.getContent(), reviewId, request.getRating());
        if (updatedReview != null) {
            return ResponseEntity.ok(updatedReview); // Return updated review
        } else {
            return ResponseEntity.notFound().build(); // Review not found or unauthorized
        }
    }

    // Delete a park review (only the review owner can delete it or if admin)
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId, @RequestParam Long userId, @RequestParam(required = false, defaultValue = "false") boolean isAdmin) {
        boolean deleted = parkReviewService.deleteReview(reviewId, userId, isAdmin);
        if (deleted) {
            return ResponseEntity.noContent().build(); // Successfully deleted
        } else {
            return ResponseEntity.status(403).build(); // Forbidden, unauthorized to delete
        }
    }
}
