package com.parkrangers.parkquest_backend.controller;

import com.parkrangers.parkquest_backend.model.response.Trip;
import com.parkrangers.parkquest_backend.service.TripService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trips")
@CrossOrigin(origins = "http://localhost:5173")
public class TripController {

    @Autowired
    private TripService tripService;

    // Retrieve trips by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Trip>> getTripsByUserId(@PathVariable Long userId) {
        List<Trip> trips = tripService.getTripsByUserId(userId);

        if (trips.isEmpty()) {
            return ResponseEntity.ok(trips != null ? trips : List.of()); // Return an empty list
        }

        return ResponseEntity.ok(trips); // Return 200 with the list of trips
    }

    // Retrieve trips by parkCode
    @GetMapping("/park/{parkCode}")
    public ResponseEntity<List<Trip>> getTripsByParkCode(@PathVariable String parkCode) {
        List<Trip> trips = tripService.getTripsByParkCode(parkCode);

        if (trips.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no trips are found
        }

        return ResponseEntity.ok(trips); // Return 200 with the list of trips
    }

    // Add or update a trip based on parkCode
    private static final Logger logger = LoggerFactory.getLogger(TripController.class);

    @PostMapping("/{parkCode}")
    public ResponseEntity<Trip> createOrUpdateTripByParkCode(
            @PathVariable String parkCode,  // Extract parkCode from the path
            @RequestBody Trip tripDetails   // Receive Trip object from JSON body
    ) {
        logger.info("POST request received to '/trips/{}' with tripDetails: {}", parkCode, tripDetails);

        try {
            // Ensure path variable "parkCode" matches the Trip object's park code
            tripDetails.setParkCode(parkCode);
            logger.debug("Set parkCode '{}' to tripDetails object.", parkCode);

            // Delegate to the service layer
            Trip updatedTrip = tripService.createOrUpdateTripByParkCode(tripDetails);
            logger.info("Trip created/updated successfully for parkCode '{}'", parkCode);

            return ResponseEntity.ok(updatedTrip);
        } catch (Exception e) {
            logger.error("Error occurred while creating/updating trip for parkCode '{}': {}", parkCode, e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }


    // Delete a trip by parkCode
    @DeleteMapping("/{tripId}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long tripId) {
        boolean success = tripService.deleteTripById(tripId);

        return success ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Add or update hiking trails for a trip based on parkCode
    @PutMapping("/{tripId}/hiking-trails")
    public ResponseEntity<Trip> addOrUpdateHikingTrails(
            @PathVariable Long tripId,
            @RequestBody Map<String, String> requestBody
    ) {
        String trailName = requestBody.get("hikingTrail");
        String trailDescription = requestBody.get("trailDescription");

        // Call the service method to handle the update
        Trip updatedTrip = tripService.addOrUpdateHikingTrails(tripId, trailName, trailDescription);

        return ResponseEntity.ok(updatedTrip);
    }

    // Delete hiking trails for a trip based on parkCode
    @DeleteMapping("/{tripId}/hiking-trails")
    public ResponseEntity<Void> deleteHikingTrails(@PathVariable Long tripId) {
        boolean success = tripService.deleteHikingTrails(tripId);

        return success ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Add or update campground data for a trip based on parkCode
    @PutMapping("/{tripId}/campgrounds")
    public ResponseEntity<Trip> addOrUpdateCampground(
            @PathVariable Long tripId,
            @RequestBody Map<String, String> requestBody
    ) {
        String campgroundName = requestBody.get("campground");
        String campgroundDescription = requestBody.get("campgroundDescription");

        Trip updatedTrip = tripService.addOrUpdateCampground(tripId, campgroundName, campgroundDescription);

        return ResponseEntity.ok(updatedTrip);
    }

    // Delete campground data for a trip based on parkCode
    @DeleteMapping("/{tripId}/campgrounds")
    public ResponseEntity<Void> deleteCampground(@PathVariable Long tripId) {
        boolean success = tripService.deleteCampground(tripId);

        return success ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Add or update startDate or endDate for a trip based on parkCode
    @PutMapping("/{tripId}/dates")
    public ResponseEntity<Trip> addOrUpdateTripDates(
            @PathVariable Long tripId,
            @RequestBody Map<String, String> requestBody
    ) {
        LocalDate startDate = requestBody.containsKey("startDate") ?
                LocalDate.parse(requestBody.get("startDate")) : null;

        LocalDate endDate = requestBody.containsKey("endDate") ?
                LocalDate.parse(requestBody.get("endDate")) : null;

        Trip updatedTrip = tripService.addOrUpdateTripDates(tripId, startDate, endDate);

        return ResponseEntity.ok(updatedTrip);
    }

    // Delete startDate or endDate for a trip based on parkCode
    @DeleteMapping("/{tripId}/dates")
    public ResponseEntity<Void> deleteTripDates(
            @PathVariable Long tripId,
            @RequestParam(value = "clearStartDate", required = false, defaultValue = "false") boolean clearStartDate,
            @RequestParam(value = "clearEndDate", required = false, defaultValue = "false") boolean clearEndDate
    ) {
        boolean success = tripService.deleteTripDates(tripId, clearStartDate, clearEndDate);

        return success ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<Trip> getTripById(@PathVariable Long tripId) {
        logger.info("Fetching trip for tripId: {}", tripId);
        Trip trip = tripService.getTripById(tripId);
        return (trip != null) ? ResponseEntity.ok(trip) : ResponseEntity.notFound().build();
    }
}
