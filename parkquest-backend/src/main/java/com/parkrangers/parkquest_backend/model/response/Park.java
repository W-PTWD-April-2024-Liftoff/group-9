package com.parkrangers.parkquest_backend.model.response;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "favorite_park")
public class Park {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "park_id")
    private Long parkId;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "parkCode")
    private String parkCode;

    @Column(name = "description")
    private String description;

    @Column(name = "url")
    private String url;

    @Transient // This means images won't be stored in the database
    private List<Image> images;

    @Transient  // Do NOT persist activities in the database
    private List<Activity> activities;

    public Park() {
    }

    public Park(Long parkId) {
    }

    public Long getParkId() {
        return parkId;
    }

    public void setParkId(Long parkId) {
        this.parkId = parkId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParkCode() {
        return parkCode;
    }

    public void setParkCode(String parkCode) {
        this.parkCode = parkCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }


    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }


    // Get the first image URL if available
    public String getFirstImageUrl() {
        return (images != null && !images.isEmpty()) ? images.get(0).getUrl() : null;
    }
}
