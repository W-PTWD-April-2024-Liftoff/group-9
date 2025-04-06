package com.parkrangers.parkquest_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name =  "favorites")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(name = "park_code", nullable = false)
    private String parkCode;

    @Column(name = "fullName", nullable = false)
    private String fullName;

    public Favorite(Long id, User user, String park, String parkCode) {
        this.id = id;
        this.user = user;
        this.fullName = fullName;
        this.parkCode = parkCode;
    }
    public Favorite() {}

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

    public String getParkCode() {
        return parkCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setParkCode(String parkCode) {
        this.parkCode = parkCode;


    }
}
