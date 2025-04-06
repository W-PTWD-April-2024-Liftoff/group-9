package com.parkrangers.parkquest_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "park_review")
public class ParkReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkReviewId;  // Review ID

    @Column(name = "user_id", nullable = false) // userId as a field
    private Long userId;

    @Column(name = "park_id", nullable = false) // parkId as a field
    private Long parkId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "rating", nullable = false)
    private int rating;

    // Getters and Setters
    public Long getParkReviewId() {
        return parkReviewId;
    }

    public void setParkReviewId(Long parkReviewId) {
        this.parkReviewId = parkReviewId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getParkId() {
        return parkId;
    }

    public void setParkId(Long parkId) {
        this.parkId = parkId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

