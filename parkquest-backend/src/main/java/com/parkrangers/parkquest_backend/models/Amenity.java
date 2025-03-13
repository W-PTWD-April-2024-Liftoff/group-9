package com.parkrangers.parkquest_backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Amenity extends AbstractEntity {

    private String name;  // Example: "Restrooms", "Campgrounds"

    @ManyToOne
    @JoinColumn(name = "park_id")
    private Park park;

    public Amenity() {}

    public Amenity(String name, Park park) {
        this.name = name;
        this.park = park;
    }


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Park getPark() { return park; }
    public void setPark(Park park) { this.park = park; }
}