package com.parkrangers.parkquest_backend.service;

import com.parkrangers.parkquest_backend.model.response.Image;
import com.parkrangers.parkquest_backend.model.response.Park;
import com.parkrangers.parkquest_backend.repository.ParkRepository;
import com.parkrangers.parkquest_backend.repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkService {
    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private UserRepository userRepository;

    //get all favorite parks for a specific user by their userId
    public List<Park> getFavoritesByUser(Long userId) {
        return parkRepository.findByUserId(userId);
    }

    public Park addFavorite(Long userId, String parkCode, String fullName, String description, String noteToSelf) {
        if (parkRepository.findByUserIdAndParkCode(userId, parkCode).isPresent()) {
            throw new RuntimeException("Favorite already exists");
        }

        Park park = new Park();
        park.setUserId(userId);
        park.setParkCode(parkCode);
        park.setFullName(fullName);
        park.setDescription(description);
        park.setNoteToSelf(noteToSelf);

        return parkRepository.save(park);
    }

    //delete an existing favorite
    public void deleteFavorite(Long userId, String parkCode) {
        parkRepository.findByUserIdAndParkCode(userId, parkCode)
                .ifPresent(parkRepository::delete);
    }

    //handle updates to noteToSelf
    public Park updateNoteToSelf(Long userId, Long parkId, String noteToSelf) {
        Park park = parkRepository.findById(parkId)
                .orElseThrow(() -> new RuntimeException("Park not found"));

        if (!park.getUserId().equals(userId)) {
            throw new RuntimeException("This park does not belong to the specified user");
        }

        park.setNoteToSelf(noteToSelf);
        return parkRepository.save(park);
    }


    //Parse a Park object from JSON
    public Park parseParkFromJson(JSONObject parkJson) throws JSONException {
        Park park = new Park();
        park.setFullName(parkJson.getString("fullName"));
        park.setParkCode(parkJson.getString("parkCode"));
        park.setDescription(parkJson.getString("description"));
        park.setUrl(parkJson.getString("url"));

        // Extract images
        if (parkJson.has("images")) {
            JSONArray imagesArray = parkJson.getJSONArray("images");
            List<Image> images = new ArrayList<>();

            for (int i = 0; i < imagesArray.length(); i++) {
                JSONObject imgJson = imagesArray.getJSONObject(i);
                Image image = new Image();
                image.setUrl(imgJson.getString("url"));
                image.setTitle(imgJson.optString("title", "")); // Optional

                images.add(image);
            }

            park.setImages(images);
        }
        return park;
    }
}
