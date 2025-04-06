package com.parkrangers.parkquest_backend.service;

import com.parkrangers.parkquest_backend.model.ParkReview;
import com.parkrangers.parkquest_backend.repository.ParkRepository;
import com.parkrangers.parkquest_backend.repository.ParkReviewRepository;
import com.parkrangers.parkquest_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkReviewService {

    @Autowired
    private ParkReviewRepository parkreviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private ParkReviewRepository parkReviewRepository;



    // Method to retrieve all park reviews for a specific park
    public List<ParkReview> getReviewsForPark(Long parkId) {
        return parkReviewRepository.findByParkId(parkId);
    }

    // Method to create a new park review
    public ParkReview createReview(Long userId, Long parkId, ParkReview review) {
        // A user can have multiple reviews for the same park
        review.setUserId(userId);  // Set the userId for the review
        review.setParkId(parkId);  // Set the parkId for the review
        return parkReviewRepository.save(review);  // Save the review
    }

    // Method to edit an existing park review (only the review owner can edit it)
    public ParkReview editReview(Long userId, Long parkReviewId, Long parkId, ParkReview newReviewData) {
        Optional<ParkReview> existingReview = parkReviewRepository.findById(parkReviewId);

        if (existingReview.isPresent()) {
            ParkReview review = existingReview.get();
            // Check if the review belongs to the user and matches the parkId
            if (review.getUserId().equals(userId) && review.getParkId().equals(parkId)) {
                review.setContent(newReviewData.getContent());
                review.setRating(newReviewData.getRating());
                return parkReviewRepository.save(review);
            } else {
                throw new SecurityException("You do not have permission to edit this review.");
            }
        } else {
            throw new IllegalArgumentException("Review not found.");
        }
    }
}

