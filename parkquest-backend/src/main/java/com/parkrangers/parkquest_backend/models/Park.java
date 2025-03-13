package com.parkrangers.parkquest_backend.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Park extends AbstractEntity {

    private String location;

    @OneToMany(mappedBy = "park")
    private List<Amenity> amenities = new ArrayList<>();

    @OneToMany(mappedBy = "park")
    private List<ContactInfo> contacts = new ArrayList<>();

    @OneToMany(mappedBy = "park")
    private List<OperatingHours> operatingHours = new ArrayList<>();

    @OneToMany(mappedBy = "park")
    private List<EntranceFee> entranceFees = new ArrayList<>();


    public Park() {}


    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public List<Amenity> getAmenities() { return amenities; }
    public void setAmenities(List<Amenity> amenities) { this.amenities = amenities; }

    public List<ContactInfo> getContacts() { return contacts; }
    public void setContacts(List<ContactInfo> contacts) { this.contacts = contacts; }

    public List<OperatingHours> getOperatingHours() { return operatingHours; }
    public void setOperatingHours(List<OperatingHours> operatingHours) { this.operatingHours = operatingHours; }

    public List<EntranceFee> getEntranceFees() { return entranceFees; }
    public void setEntranceFees(List<EntranceFee> entranceFees) { this.entranceFees = entranceFees; }
}