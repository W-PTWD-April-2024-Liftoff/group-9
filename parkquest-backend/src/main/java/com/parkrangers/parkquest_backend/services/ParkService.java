package com.parkrangers.parkquest_backend.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ParkService {

    private static final String API_BASE_URL = "https://developer.nps.gov/api/v1/parks";
    private static final String API_KEY = "psiKQ0pcuRwnHB779eAfD9G0Ihuf7iyqD0QejP79";

    private final RestTemplate restTemplate = new RestTemplate();

    public JsonNode fetchParksByState(String stateCode) {
        String url = API_BASE_URL + "?stateCode=" + stateCode + "&api_key=" + API_KEY;
        return restTemplate.getForObject(url, JsonNode.class);
    }
}


