package com.parkrangers.parkquest_backend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    @Column(name = "review_text")
    private String reviewText;  // Review content
    @Column(name="rating")
    private int rating;         // Rating out of 5
    @Column (name="park_id")
    private String parkId;      // parkId as a simple string (not linked to the Park entity)

    // Optionally, you can still associate the user if needed
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}