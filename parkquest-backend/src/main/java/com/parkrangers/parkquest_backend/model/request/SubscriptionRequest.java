package com.parkrangers.parkquest_backend.model.request;


public class SubscriptionRequest {

    private Long userId;
    private String parkCode;



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
}