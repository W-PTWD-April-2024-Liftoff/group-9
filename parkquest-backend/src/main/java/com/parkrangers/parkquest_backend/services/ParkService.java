package com.parkrangers.parkquest_backend.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.parkrangers.parkquest_backend.models.Park;
import com.parkrangers.parkquest_backend.repositories.ParkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ParkService {

    private static final String API_BASE_URL = "https://developer.nps.gov/api/v1/parks";
    private static final String API_KEY = "psiKQ0pcuRwnHB779eAfD9G0Ihuf7iyqD0QejP79";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ParkRepository parkRepository;

    @Autowired
    public ParkService(ParkRepository parkRepository) {
        this.parkRepository = parkRepository;
    }

    public JsonNode fetchParksByState(String stateCode) {
        String url = API_BASE_URL + "?stateCode=" + stateCode + "&api_key=" + API_KEY;
        return restTemplate.getForObject(url, JsonNode.class);
    }

    public void fetchAndSaveParks(String stateCode) {
        JsonNode response = fetchParksByState(stateCode);

        if (response != null && response.has("data")) {
            for (JsonNode parkNode : response.get("data")) {
                Park park = new Park();
                park.setParkId(parkNode.get("id").asText());
                park.setFullName(parkNode.get("fullName").asText());
                park.setStates(parkNode.get("states").asText());

                parkRepository.save(park);
            }
        }
    }
}




