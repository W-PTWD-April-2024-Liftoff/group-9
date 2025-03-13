package com.parkrangers.parkquest_backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OperatingHours extends AbstractEntity {

    private String day;
    private String openingTime;
    private String closingTime;

    @ManyToOne
    @JoinColumn(name = "park_id")
    private Park park;


    public OperatingHours() {}

    public OperatingHours(String day, String openingTime, String closingTime, Park park) {
        this.day = day;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.park = park;
    }

    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }

    public String getOpeningTime() { return openingTime; }
    public void setOpeningTime(String openingTime) { this.openingTime = openingTime; }

    public String getClosingTime() { return closingTime; }
    public void setClosingTime(String closingTime) { this.closingTime = closingTime; }

    public Park getPark() { return park; }
    public void setPark(Park park) { this.park = park; }
}
