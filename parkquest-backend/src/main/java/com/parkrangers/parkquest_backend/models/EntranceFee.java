package com.parkrangers.parkquest_backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class EntranceFee extends AbstractEntity {

    private double amount;
    private String description;

    @ManyToOne
    @JoinColumn(name = "park_id")
    private Park park;


    public EntranceFee() {}

    public EntranceFee(double amount, String description, Park park) {
        this.amount = amount;
        this.description = description;
        this.park = park;
    }


    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Park getPark() { return park; }
    public void setPark(Park park) { this.park = park; }
}