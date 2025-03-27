package com.parkrangers.parkquest_backend.controllers;

import com.parkrangers.parkquest_backend.models.response.Park;
import com.parkrangers.parkquest_backend.service.ParkSearchService;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkSearchController {

    @Autowired
    private ParkSearchService parkSearchService;

    @GetMapping("/parks/searches")
    public List<Park> getParks(@RequestParam(required = false) String stateCode,
                               @RequestParam(required = false) String parkName) throws JSONException {
        if (parkName != null && !parkName.isEmpty()) {
            return parkSearchService.getParksByName(parkName);
        } else if (stateCode != null && !stateCode.isEmpty()) {
            return parkSearchService.getParks(stateCode);
        } else {
            throw new IllegalArgumentException("Either stateCode or parkName must be provided.");
        }
    }
}

