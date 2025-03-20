package com.parkrangers.parkquest_backend.models;


import com.parkrangers.parkquest_backend.models.response.Park;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TripPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_plan_id")
    private Long tripPlanId;


    @ManyToMany
    @JoinTable(
            name = "tripplan_park",
            joinColumns = @JoinColumn(name = "trip_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "park_id")
    )
    private List<Park> parks = new ArrayList<>();

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    private String startDate;
    private String endDate;

    public TripPlan() {}

    public List<Park> getParks() {
        return parks;
    }

    public void setParks(List<Park> parks) {
        this.parks = parks;
    }

    public Long getTripPlanId() {
        return tripPlanId;
    }

    public void setTripPlanId(Long tripPlanId) {
        this.tripPlanId = tripPlanId;
    }
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}

