package com.parkrangers.parkquest_backend.service;

import com.parkrangers.parkquest_backend.model.response.Park;
import com.parkrangers.parkquest_backend.model.response.ParkSearchResponse;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;


@Service
public class ParkSearchService {

    @Value("${api.key}")
    private String apiKey;

    private final String API_URL = "https://developer.nps.gov/api/v1/parks";

    public List<Park> getParks(String stateCode) throws JSONException {
        String url = String.format("%s?stateCode=%s&limit=100&api_key=%s",
                API_URL, stateCode, apiKey);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ParkSearchResponse> response = restTemplate.getForEntity(url, ParkSearchResponse.class);

        return response.getBody().getData();
    }

    public List<Park> getParksByName(String parkName) throws JSONException {
        String url = String.format("%s?q=%s&limit=100&api_key=%s",
                API_URL, parkName, apiKey);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ParkSearchResponse> response = restTemplate.getForEntity(url, ParkSearchResponse.class);

        // Filter the results to include only exact matches in the fullName field
        return response.getBody().getData().stream()
                .filter(park -> park.getFullName().toLowerCase().contains(parkName.toLowerCase()))
                .toList();
    }
}
