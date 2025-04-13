package com.parkrangers.parkquest_backend.service;

import com.parkrangers.parkquest_backend.model.ParkReview;
import com.parkrangers.parkquest_backend.repository.ParkReviewRepository;
import com.parkrangers.parkquest_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.parkrangers.parkquest_backend.model.User;


import java.util.List;
import java.util.Optional;

@Service
public class ParkReviewService {

    @Autowired
    private ParkReviewRepository parkReviewRepository;

    @Autowired
    private UserRepository userRepository;

    // Method to retrieve all park reviews for a specific park
   public List<ParkReview> getReviewsForPark(String parkCode) {
    List<ParkReview> reviews = parkReviewRepository.findByParkCode(parkCode);

    for (ParkReview review : reviews) {
        String username = userRepository.findById(review.getUserId())
                .map(User::getUsername)
                .orElse("Unknown User");
        review.setUsername(username);  // Set only the transient field
    }

    return reviews;
}

    // Method to create a new park review
    public ParkReview createReview(Long userId, String parkCode, String content, int rating) {
        ParkReview review = new ParkReview();
        review.setUserId(userId);
        review.setParkCode(parkCode);
        review.setContent(content);
        review.setRating(rating);

        // Fetch username and set it in review (for return value)
        String username = userRepository.findById(userId)
                .map(user -> user.getUsername())
                .orElse("Unknown User");
        review.setUsername(username);

        return parkReviewRepository.save(review);
    }

    // Method to edit an existing park review (only the review owner can edit it)
    public ParkReview editReview(Long userId, String parkCode, String content, Long reviewId, int rating) {
        Optional<ParkReview> existingReview = parkReviewRepository.findById(reviewId);
        if (existingReview.isEmpty()) {
            return null;
        }

        ParkReview review = existingReview.get();
        if (!review.getUserId().equals(userId)) {
            throw new SecurityException("User does not have permission to edit this review");
        }

        review.setContent(content);
        review.setRating(rating);

        return parkReviewRepository.save(review);
    }

    // Method to delete a review
    public boolean deleteReview(Long reviewId, Long userId, boolean isAdmin) {
        Optional<ParkReview> reviewOptional = parkReviewRepository.findById(reviewId);
        if (reviewOptional.isPresent()) {
            ParkReview review = reviewOptional.get();
            if (review.getUserId().equals(userId) || isAdmin) {
                parkReviewRepository.delete(review);
                return true;
            }
        }
        return false;
    }
}
