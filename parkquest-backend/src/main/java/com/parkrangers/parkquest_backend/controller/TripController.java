package com.parkrangers.parkquest_backend.controller;

import com.parkrangers.parkquest_backend.model.response.Trip;
import com.parkrangers.parkquest_backend.service.TripService;
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
            return ResponseEntity.notFound().build(); // Return 404 if no trips are found
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
    @PostMapping("/{parkCode}")
    public ResponseEntity<Trip> createOrUpdateTripByParkCode(
            @PathVariable String parkCode,
            @RequestBody Trip tripDetails) {

        // Set the parkCode from the URL path to ensure consistency
        tripDetails.setParkCode(parkCode);

        // Delegate to service and handle the result
        Trip updatedTrip = tripService.createOrUpdateTripByParkCode(tripDetails);
        if (updatedTrip == null) {
            return ResponseEntity.badRequest().body(null); // Return 400 if invalid input
        }
        return ResponseEntity.ok(updatedTrip); // Return the created/updated trip
    }

    // Delete a trip by parkCode
    @DeleteMapping("/{parkCode}")
    public ResponseEntity<Void> deleteTripByParkCode(@PathVariable String parkCode) {
        boolean success = tripService.deleteTripByParkCode(parkCode);

        if (!success) {
            return ResponseEntity.notFound().build(); // Return 404 if no trip is found to delete
        }
        return ResponseEntity.noContent().build(); // Return 204 No Content on success
    }

    // Add or update hiking trails for a trip based on parkCode
    @PutMapping("/{parkCode}/hiking-trails")
    public ResponseEntity<Trip> addOrUpdateHikingTrails(
            @PathVariable String parkCode,
            @RequestBody Map<String, String> requestBody) {

        String hikingTrail = requestBody.get("hikingTrail");
        String trailDescription = requestBody.get("trailDescription");

        if (hikingTrail == null || trailDescription == null) {
            return ResponseEntity.badRequest().body(null); // Ensure both fields are provided
        }

        Trip updatedTrip = tripService.addOrUpdateHikingTrails(parkCode, hikingTrail, trailDescription);
        if (updatedTrip == null) {
            return ResponseEntity.notFound().build(); // Trip not found
        }
        return ResponseEntity.ok(updatedTrip);
    }

    // Delete hiking trails for a trip based on parkCode
    @DeleteMapping("/{parkCode}/hiking-trails")
    public ResponseEntity<Void> deleteHikingTrails(@PathVariable String parkCode) {
        boolean success = tripService.deleteHikingTrails(parkCode);
        if (!success) {
            return ResponseEntity.notFound().build(); // Trip not found
        }
        return ResponseEntity.noContent().build(); // Successfully deleted
    }

    // Add or update campground data for a trip based on parkCode
    @PutMapping("/{parkCode}/campgrounds")
    public ResponseEntity<Trip> addOrUpdateCampground(
            @PathVariable String parkCode,
            @RequestBody Map<String, String> requestBody) {

        String campground = requestBody.get("campground");
        String campgroundDescription = requestBody.get("campgroundDescription");

        if (campground == null || campgroundDescription == null) {
            return ResponseEntity.badRequest().body(null); // Ensure fields are provided
        }

        Trip updatedTrip = tripService.addOrUpdateCampground(parkCode, campground, campgroundDescription);
        if (updatedTrip == null) {
            return ResponseEntity.notFound().build(); // Trip not found
        }
        return ResponseEntity.ok(updatedTrip);
    }

    // Delete campground data for a trip based on parkCode
    @DeleteMapping("/{parkCode}/campgrounds")
    public ResponseEntity<Void> deleteCampground(@PathVariable String parkCode) {
        boolean success = tripService.deleteCampground(parkCode);
        if (!success) {
            return ResponseEntity.notFound().build(); // Trip not found
        }
        return ResponseEntity.noContent().build(); // Successfully deleted
    }

    // Add or update startDate or endDate for a trip based on parkCode
    @PutMapping("/{parkCode}/dates")
    public ResponseEntity<Trip> addOrUpdateTripDates(
            @PathVariable String parkCode,
            @RequestBody Map<String, String> requestBody) {

        // Parse dates from request body
        LocalDate startDate = requestBody.containsKey("startDate") ?
                LocalDate.parse(requestBody.get("startDate")) : null;
        LocalDate endDate = requestBody.containsKey("endDate") ?
                LocalDate.parse(requestBody.get("endDate")) : null;

        if (startDate == null && endDate == null) {
            return ResponseEntity.badRequest().body(null); // At least one date must be provided
        }

        Trip updatedTrip = tripService.addOrUpdateTripDates(parkCode, startDate, endDate);
        if (updatedTrip == null) {
            return ResponseEntity.notFound().build(); // Trip not found
        }
        return ResponseEntity.ok(updatedTrip);
    }

    // Delete startDate or endDate for a trip based on parkCode
    @DeleteMapping("/{parkCode}/dates")
    public ResponseEntity<Void> deleteTripDates(
            @PathVariable String parkCode,
            @RequestParam(value = "clearStartDate", required = false, defaultValue = "false") boolean clearStartDate,
            @RequestParam(value = "clearEndDate", required = false, defaultValue = "false") boolean clearEndDate) {

        if (!clearStartDate && !clearEndDate) {
            return ResponseEntity.badRequest().build(); // At least one of the parameters must be true
        }

        boolean success = tripService.deleteTripDates(parkCode, clearStartDate, clearEndDate);
        if (!success) {
            return ResponseEntity.notFound().build(); // Trip not found
        }
        return ResponseEntity.noContent().build(); // Successfully deleted
    }
}
