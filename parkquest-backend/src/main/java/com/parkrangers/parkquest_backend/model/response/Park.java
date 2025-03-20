package com.parkrangers.parkquest_backend.models.response;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "favorite_park")
public class Park {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "park_id")
    private Long parkId;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @ElementCollection
    @CollectionTable(name = "park_activities", joinColumns = @JoinColumn(name = "park_id"))
    @Column(name = "activity")
    private List<String> activities;

    @Column(name = "image_url")
    private String imageUrl;

    public Park() {
    }

    public Long getParkId() {
        return parkId;
    }

    public void setParkId(Long parkId) {
        this.parkId = parkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getActivities() {
        return activities;
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}