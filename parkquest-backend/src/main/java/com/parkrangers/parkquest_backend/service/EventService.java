package com.parkrangers.parkquest_backend.service;

import com.parkrangers.parkquest_backend.model.response.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class EventService {

    @Value("${api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Event> fetchEventsByParkCode(String parkCode) {
        String url = "https://developer.nps.gov/api/v1/events?parkCode=" + parkCode + "&api_key=" + apiKey;
        System.out.println("Calling NPS Events API: " + url);

        try {
            NpsEventResponse response = restTemplate.getForObject(url, NpsEventResponse.class);
            return response != null ? response.getData() : List.of();
        } catch (Exception e) {
            System.out.println(" Error fetching events: " + e.getMessage());
            return List.of();
        }
    }


    public static class NpsEventResponse {
        private List<Event> data;

        public List<Event> getData() { return data; }
        public void setData(List<Event> data) { this.data = data; }
    }
}