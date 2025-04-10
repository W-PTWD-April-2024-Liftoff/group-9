package com.parkrangers.parkquest_backend.model.response;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "my_trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;

//    @ManyToOne
//    @JoinColumn
    @Column(name = "user_id", nullable = false)
    private Long userId;

//    @ManyToOne
//    @JoinColumn
    @Column(name = "parkCode", nullable = false)
    private String parkCode;

    @Column(name = "park_name", nullable = false)
    private String parkName;

    @Column(name = "park_description")
    private String parkDescription;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "hiking_trail")
    private String hikingTrail;

    @Column(name = "trail_description")
    private String trailDescription;

    @Column(name = "campground")
    private String campground;

    @Column(name = "campground_description")
    private String campgroundDescription;

    // Constructors, getters, and setters
    public Trip() {}

    public Trip(Long tripId, Long userId, String parkCode, String parkName, String parkDescription, LocalDate startDate, LocalDate endDate, String hikingTrail, String trailDescription, String campground, String campgroundDescription) {
        this.tripId = tripId;
        this.userId = userId;
        this.parkCode = parkCode;
        this.parkName = parkName;
        this.parkDescription = parkDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hikingTrail = hikingTrail;
        this.trailDescription = trailDescription;
        this.campground = campground;
        this.campgroundDescription = campgroundDescription;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getParkCode() {
        return parkCode;
    }

    public void setParkCode(String parkCode) {
        this.parkCode = parkCode;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getParkDescription() {
        return parkDescription;
    }

    public void setParkDescription(String parkDescription) {
        this.parkDescription = parkDescription;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getHikingTrail() {
        return hikingTrail;
    }

    public void setHikingTrail(String hikingTrail) {
        this.hikingTrail = hikingTrail;
    }

    public String getTrailDescription() {
        return trailDescription;
    }

    public void setTrailDescription(String trailDescription) {
        this.trailDescription = trailDescription;
    }

    public String getCampground() {
        return campground;
    }

    public void setCampground(String campground) {
        this.campground = campground;
    }

    public String getCampgroundDescription() {
        return campgroundDescription;
    }

    public void setCampgroundDescription(String campgroundDescription) {
        this.campgroundDescription = campgroundDescription;
    }
}
