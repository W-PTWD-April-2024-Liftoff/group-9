package com.parkrangers.parkquest_backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ContactInfo extends AbstractEntity {

    private String phone;
    private String email;

    @ManyToOne
    @JoinColumn(name = "park_id")
    private Park park;

    public ContactInfo() {}

    public ContactInfo(String phone, String email, Park park) {
        this.phone = phone;
        this.email = email;
        this.park = park;
    }

    // Getters and Setters
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Park getPark() { return park; }
    public void setPark(Park park) { this.park = park; }
}
