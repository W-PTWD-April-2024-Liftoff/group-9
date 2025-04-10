package com.parkrangers.parkquest_backend.model.response;

import com.parkrangers.parkquest_backend.model.User;
import jakarta.persistence.*;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "park_code", nullable = false)
    private Park park;

    // Default constructor
    public Subscription() {}

    // Constructor
    public Subscription(User user, Park park) {
        this.user = user;
        this.park = park;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Park getPark() {
        return park;
    }

    public void setPark(Park park) {
        this.park = park;
    }

    @Override
    public String toString() {
        return "Subscription{id=" + id + ", user=" + user + ", park=" + park + "}";
    }
}