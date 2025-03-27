package com.parkrangers.parkquest_backend.controller;

import com.parkrangers.parkquest_backend.models.entity.ParkReview;
import com.parkrangers.parkquest_backend.models.entity.User;
import com.parkrangers.parkquest_backend.models.request.ParkReviewRequest;
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


    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ParkReview addReview(@RequestBody ParkReviewRequest request) {
        User user = userService.getAuthenticatedUser();

        ParkReview review = new ParkReview();
        review.setReviewText(request.getReviewText());
        review.setRating(request.getRating());
        review.setParkId(request.getParkId());
        review.setUser(user);

        return parkReviewService.addReview(review);
    }


    @GetMapping("/{parkId}")
    public List<ParkReview> getReviewsByPark(@PathVariable String parkId) {
        return parkReviewService.getReviewsByPark(parkId);
    }


    @PutMapping("/{reviewId}")
    public ParkReview updateReview(@PathVariable Long reviewId, @RequestBody ParkReviewRequest request) {
        ParkReview existingReview = parkReviewService.getReviewById(reviewId);
        existingReview.setReviewText(request.getReviewText());
        existingReview.setRating(request.getRating());
        existingReview.setParkId(request.getParkId());
        return parkReviewService.updateReview(existingReview);
    }


    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable Long reviewId) {
        parkReviewService.deleteReview(reviewId);
    }
}
