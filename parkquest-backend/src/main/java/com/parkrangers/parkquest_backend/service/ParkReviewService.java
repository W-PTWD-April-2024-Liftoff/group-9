//package com.parkrangers.parkquest_backend.service;
//
//import com.parkrangers.parkquest_backend.model.entity.ParkReview;
//import com.parkrangers.parkquest_backend.repository.ParkReviewRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ParkReviewService {
//
//    private final ParkReviewRepository parkReviewRepository;
//
//    // Constructor to inject the repository
//    public ParkReviewService(ParkReviewRepository parkReviewRepository) {
//        this.parkReviewRepository = parkReviewRepository;
//    }
//
//    // Add a new review
//    public ParkReview addReview(ParkReview review) {
//        return parkReviewRepository.save(review);
//    }
//
//    // Get all reviews for a specific park by parkId
//    public List<ParkReview> getReviewsByPark(String parkId) {
//        return parkReviewRepository.findByParkId(parkId);
//    }
//
//    // Get a review by its reviewId
//    public ParkReview getReviewById(Long reviewId) {
//        Optional<ParkReview> review = parkReviewRepository.findByReviewId(reviewId);
//        return review.orElseThrow(() -> new RuntimeException("Review not found"));
//    }
//
//    // Update an existing review
//    public ParkReview updateReview(ParkReview review) {
//        // Optionally, you can check if the review exists before saving it, though this depends on your use case
//        return parkReviewRepository.save(review);
//    }
//
//    // Delete a review by its reviewId
//    public void deleteReview(Long reviewId) {
//        // Make sure to handle the case where the review might not exist
//        if (parkReviewRepository.existsById(reviewId)) {
//            parkReviewRepository.deleteById(reviewId);
//        } else {
//            throw new RuntimeException("Review not found");
//        }
//    }
//}
