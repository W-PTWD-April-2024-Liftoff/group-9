package com.parkrangers.parkquest_backend.model.response;

import java.util.List;

public class ParkSearchResponse {

    private List<Park> data;

    public List<Park> getData() {
        return data;
    }

    public void setData(List<Park> data) {
        this.data = data;
    }
}
