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

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "parkCode")
    private String parkCode;

    @Column(name = "description")
    private String description;

    @Column(name = "url")
    private String url;

    @Column(name = "note_to_self")
    private String noteToSelf;

    @Transient // This means images won't be stored in the database
    private List<Image> images;

    @Transient  // Do NOT persist activities in the database
    private List<Activity> activities;

    @Transient
    private List<Address> addresses;

    public Park() {
    }

    public Park(Long parkId, Long userId, String fullName, String parkCode, String description, String url, String noteToSelf) {
        this.parkId = parkId;
        this.userId = userId;
        this.fullName = fullName;
        this.parkCode = parkCode;
        this.description = description;
        this.noteToSelf = noteToSelf;
    }

    public String getNoteToSelf() {
        return noteToSelf;
    }

    public void setNoteToSelf(String noteToSelf) {
        this.noteToSelf = noteToSelf;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Park(String parkCode) {
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

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    // Get the first image URL if available
    public String getFirstImageUrl() {
        return (images != null && !images.isEmpty()) ? images.get(0).getUrl() : null;
    }
}
