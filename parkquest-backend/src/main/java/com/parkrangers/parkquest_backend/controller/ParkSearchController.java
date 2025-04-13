package com.parkrangers.parkquest_backend.controller;

import com.parkrangers.parkquest_backend.model.response.Park;
import com.parkrangers.parkquest_backend.service.ParkSearchService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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

    @GetMapping("/lookup")
    public Park getParksByCode(@RequestParam(required = true) String parkCode) throws JSONException {
        if (parkCode == null || parkCode.isBlank()) {
            throw new IllegalArgumentException("parkCode must be provided.");
        }
        return parkSearchService.getParkByCode(parkCode);
    }

}

