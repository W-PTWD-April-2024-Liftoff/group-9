package com.parkrangers.parkquest_backend.controller;

import com.parkrangers.parkquest_backend.model.ParkReview;
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
    public ResponseEntity<ParkReview> addReview(
            @RequestParam Long userId,
            @RequestParam String parkCode,
            @RequestBody String content,
            @RequestParam int rating
    ){
        ParkReview review = parkreviewService.createReview(userId, parkCode, content, rating);
        return ResponseEntity.ok(review);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteReview(@RequestParam Long userId, @RequestParam String parkCode) {
        parkService.deleteFavorite(userId, parkCode);
        return ResponseEntity.ok().build();
    }
    // Edit an existing park review (only the review owner can edit it)
    @PutMapping
    public ResponseEntity<ParkReview> editReview(
            @RequestParam Long userId,
            @RequestParam String parkCode,
            @RequestBody String content,
            @RequestParam int rating
    ){
        ParkReview updatedReview = parkreviewService.editReview(userId, parkCode, content, rating);
        if (updatedReview != null) {
            return ResponseEntity.ok(updatedReview);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
