package com.parkrangers.parkquest_backend.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Park extends AbstractEntity {

    @NotBlank
    private String parkId;

    @NotBlank
    private String state;

    @Column
    private String description;





    public Park() {}

    public Park(String parkId, String state, String description) {
        this.parkId = parkId;
        this.state = state;
        this.description = description;
    }

    public String getParkId() { return parkId; }
    public void setParkId(String parkId) { this.parkId = parkId; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

}