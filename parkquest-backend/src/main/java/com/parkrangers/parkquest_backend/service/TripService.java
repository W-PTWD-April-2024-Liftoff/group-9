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
    public boolean deleteTripById(Long tripId) {
        if (!tripRepository.existsById(tripId)) {
            return false;
        }

        tripRepository.deleteById(tripId);
        return true;
    }

    // Add or update hiking trails and trail description for a trip
    public Trip addOrUpdateHikingTrails(Long tripId, String hikingTrail, String trailDescription) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);

        if (optionalTrip.isEmpty()) {
            throw new IllegalStateException("Trip with ID " + tripId + " not found.");
        }

        Trip trip = optionalTrip.get();
        trip.setHikingTrail(hikingTrail);
        trip.setTrailDescription(trailDescription);

        return tripRepository.save(trip);
    }

    // Delete hiking trails for a trip by parkCode
    public boolean deleteHikingTrails(Long tripId) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);

        if (optionalTrip.isEmpty()) {
            return false;
        }

        Trip trip = optionalTrip.get();
        trip.setHikingTrail(null);
        trip.setTrailDescription(null);

        tripRepository.save(trip);
        return true;
    }

    // Add or update a campground and campground description for a trip
    public Trip addOrUpdateCampground(Long tripId, String campground, String campgroundDescription) {
        Optional<Trip> tripOptional = tripRepository.findById(tripId);
        if (!tripOptional.isPresent()) {
            throw new IllegalArgumentException("Trip not found");
        }
        Trip trip = tripOptional.get();

        // Update campground fields
        trip.setCampground(campground);
        trip.setCampgroundDescription(campgroundDescription);

        // Save to the database
        return tripRepository.save(trip);
    }

    // Delete campground information for a trip by parkCode
    public boolean deleteCampground(Long tripId) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);

        if (optionalTrip.isEmpty()) {
            return false;
        }

        Trip trip = optionalTrip.get();
        trip.setCampground(null);
        trip.setCampgroundDescription(null);

        tripRepository.save(trip);
        return true;
    }

    // Add or update startDate or endDate for a trip
    public Trip addOrUpdateTripDates(Long tripId, LocalDate startDate, LocalDate endDate) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);

        if (optionalTrip.isEmpty()) {
            throw new IllegalStateException("Trip with ID " + tripId + " not found.");
        }

        Trip trip = optionalTrip.get();
        trip.setStartDate(startDate);
        trip.setEndDate(endDate);

        return tripRepository.save(trip);
    }

    // Delete startDate or endDate for a trip
    public boolean deleteTripDates(Long tripId, boolean clearStartDate, boolean clearEndDate) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);

        if (optionalTrip.isEmpty()) {
            return false;
        }

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

    public Trip getTripById(Long tripId) {
        return tripRepository.findById(tripId).orElse(null); // Use repository to find trip by its ID
    }
}
