package com.parkrangers.parkquest_backend.controller;

import com.parkrangers.parkquest_backend.model.entity.ParkReview;
import com.parkrangers.parkquest_backend.model.entity.User;
import com.parkrangers.parkquest_backend.model.request.ParkReviewRequest;
import com.parkrangers.parkquest_backend.service.ParkReviewService;
import com.parkrangers.parkquest_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("parks/reviews")
public class ParkReviewController {

    private final ParkReviewService parkReviewService;
    private final UserService userService;

    public ParkReviewController(ParkReviewService parkReviewService, UserService userService) {
        this.parkReviewService = parkReviewService;
        this.userService = userService;
    }

    // Create a new review
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ParkReview addReview(@RequestBody ParkReviewRequest request) {
        User user = userService.getAuthenticatedUser();
        ParkReview review = new ParkReview();
        review.setReviewText(request.getReviewText());
        review.setRating(request.getRating());
        review.setParkId(request.getParkId());  // Set the parkId from the request
        review.setUser(user); // Associate the review with the current user

        return parkReviewService.addReview(review);
    }

    // Get all reviews for a specific park (by parkId)
    @GetMapping("/{parkId}")
    public List<ParkReview> getReviewsByPark(@PathVariable String parkId) {
        return parkReviewService.getReviewsByPark(parkId);
    }

    // Update an existing review
    @PutMapping("/{reviewId}")
    public ParkReview updateReview(@PathVariable Long reviewId, @RequestBody ParkReviewRequest request) {
        ParkReview existingReview = parkReviewService.getReviewById(reviewId);
        existingReview.setReviewText(request.getReviewText());
        existingReview.setRating(request.getRating());
        existingReview.setParkId(request.getParkId());

        return parkReviewService.updateReview(existingReview);
    }

    // Delete a review by reviewId
    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable Long reviewId) {
        parkReviewService.deleteReview(reviewId);
    }
}

