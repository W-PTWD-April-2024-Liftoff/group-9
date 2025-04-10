package com.parkrangers.parkquest_backend.service;

import com.parkrangers.parkquest_backend.model.response.Trip;
import com.parkrangers.parkquest_backend.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class TripService {
    @Autowired
    private TripRepository tripRepository;

    // Retrieve trips by userId
    public List<Trip> getTripsByUserId(Long userId) {
        return tripRepository.findByUserId(userId);
    }

    // Retrieve trips by parkCode
    public List<Trip> getTripsByParkCode(String parkCode) {
        return tripRepository.findByParkCode(parkCode);
    }

    // Create or update a trip based on the parkCode
    public Trip createOrUpdateTripByParkCode(Trip tripDetails) {
        // Fetch trips based on the parkCode
        List<Trip> existingTrips = tripRepository.findByParkCode(tripDetails.getParkCode());

        if (!existingTrips.isEmpty()) {
            // Assume only the first trip needs to be updated if multiple entries exist
            Trip trip = existingTrips.get(0);

            // Update the existing trip with the provided details
            trip.setUserId(tripDetails.getUserId());
            trip.setParkCode(tripDetails.getParkCode());
            trip.setParkName(tripDetails.getParkName());
            trip.setParkDescription(tripDetails.getParkDescription());
            trip.setStartDate(tripDetails.getStartDate());
            trip.setEndDate(tripDetails.getEndDate());
            trip.setHikingTrail(tripDetails.getHikingTrail());
            trip.setTrailDescription(tripDetails.getTrailDescription());
            trip.setCampground(tripDetails.getCampground());
            trip.setCampgroundDescription(tripDetails.getCampgroundDescription());

            // Save updated trip
            return tripRepository.save(trip);
        }

        // If no valid trip exists for the parkCode, create a new one
        return tripRepository.save(tripDetails);
    }

    // Delete a trip by parkCode
    public boolean deleteTripByParkCode(String parkCode) {
        // Check for the existence of trips by parkCode
        if (tripRepository.findByParkCode(parkCode).isEmpty()) {
            return false; // No trips found for the given parkCode
        }

        // Delete all trips matching the parkCode
        tripRepository.deleteByParkCode(parkCode);
        return true;
    }

    // Add or update hiking trails and trail description for a trip
    public Trip addOrUpdateHikingTrails(String parkCode, String hikingTrail, String trailDescription) {
        // Find the trip by parkCode
        Optional<Trip> optionalTrip = tripRepository.findByParkCode(parkCode).stream().findFirst(); // Assumes unique parkCode
        if (optionalTrip.isEmpty()) {
            return null;  // Trip with the given parkCode not found
        }

        // Update fields and save the trip
        Trip trip = optionalTrip.get();
        trip.setHikingTrail(hikingTrail);
        trip.setTrailDescription(trailDescription);

        return tripRepository.save(trip);
    }

    // Delete hiking trails for a trip by parkCode
    public boolean deleteHikingTrails(String parkCode) {
        // Find the trip by parkCode
        Optional<Trip> optionalTrip = tripRepository.findByParkCode(parkCode).stream().findFirst();
        if (optionalTrip.isEmpty()) {
            return false; // Trip with parkCode not found
        }

        // Update fields to clear hiking trails and save
        Trip trip = optionalTrip.get();
        trip.setHikingTrail(null);
        trip.setTrailDescription(null);

        tripRepository.save(trip);
        return true;
    }

    // Add or update a campground and campground description for a trip
    public Trip addOrUpdateCampground(String parkCode, String campground, String campgroundDescription) {
        // Find the trip by parkCode
        Optional<Trip> optionalTrip = tripRepository.findByParkCode(parkCode).stream().findFirst(); // Assumes unique parkCode
        if (optionalTrip.isEmpty()) {
            return null;  // Trip with the given parkCode not found
        }

        // Update fields and save the trip
        Trip trip = optionalTrip.get();
        trip.setCampground(campground);
        trip.setCampgroundDescription(campgroundDescription);

        return tripRepository.save(trip);
    }

    // Delete campground information for a trip by parkCode
    public boolean deleteCampground(String parkCode) {
        // Find the trip by parkCode
        Optional<Trip> optionalTrip = tripRepository.findByParkCode(parkCode).stream().findFirst();
        if (optionalTrip.isEmpty()) {
            return false;  // Trip with the given parkCode not found
        }

        // Update fields to clear campground data and save
        Trip trip = optionalTrip.get();
        trip.setCampground(null);
        trip.setCampgroundDescription(null);

        tripRepository.save(trip);
        return true;
    }

    // Add or update startDate or endDate for a trip
    public Trip addOrUpdateTripDates(String parkCode, LocalDate startDate, LocalDate endDate) {
        // Find the trip by parkCode
        Optional<Trip> optionalTrip = tripRepository.findByParkCode(parkCode).stream().findFirst(); // Assumes unique parkCode
        if (optionalTrip.isEmpty()) {
            return null; // Trip with the given parkCode not found
        }

        // Update fields and save the trip
        Trip trip = optionalTrip.get();
        if (startDate != null) {
            trip.setStartDate(startDate);
        }
        if (endDate != null) {
            trip.setEndDate(endDate);
        }

        return tripRepository.save(trip);
    }

    // Delete startDate or endDate for a trip
    public boolean deleteTripDates(String parkCode, boolean clearStartDate, boolean clearEndDate) {
        // Find the trip by parkCode
        Optional<Trip> optionalTrip = tripRepository.findByParkCode(parkCode).stream().findFirst();
        if (optionalTrip.isEmpty()) {
            return false; // Trip with the given parkCode not found
        }

        // Clear the specified date fields and save
        Trip trip = optionalTrip.get();
        if (clearStartDate) {
            trip.setStartDate(null);
        }
        if (clearEndDate) {
            trip.setEndDate(null);
        }

        tripRepository.save(trip);
        return true;
    }

}
