package com.parkrangers.parkquest_backend.service;

import com.parkrangers.parkquest_backend.model.Favorite;
import com.parkrangers.parkquest_backend.model.User;
import com.parkrangers.parkquest_backend.model.response.Park;
import com.parkrangers.parkquest_backend.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    // Get favorites by userId
    public List<Favorite> getFavoritesByUser(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }


//    public List<Favorite> getFavoritesByUser(Long userId) {
//        return favoriteRepository.findByUserId(userId).stream()
//                .map(fav -> {
//                    // Check if park object is null or incomplete
//                    if (fav.getPark() == null || fav.getPark().getParkCode() == null) {
//                        throw new RuntimeException("Favorite contains incomplete park data");
//                    }
//                    return fav;
//                })
//                .toList();
//    }



    public Favorite addFavorite(Long userId, String parkCode, String fullName) {
        if (parkCode == null || parkCode.isBlank()) {
            throw new RuntimeException("Invalid Park Code: Cannot add to favorites");
        }

        // Check if the favorite already exists
        if (favoriteRepository.findByUserIdAndParkCode(userId, parkCode).isPresent()) {
            throw new RuntimeException("Favorite already exists");
        }

        // Create and save the favorite
        Favorite favorite = new Favorite();
        favorite.setUser(new User(userId));
        Park park = new Park(); // Temporary object to set parkCode
        park.setParkCode(parkCode);
        park.setFullName(parkCode);
        return favoriteRepository.save(favorite);
    }

    // Remove a favorite by parkCode
    public void deleteFavorite(Long userId, String parkCode) {
        Favorite favorite = favoriteRepository.findByUserIdAndParkCode(userId, parkCode)
                .orElseThrow(() -> new RuntimeException("Favorite not found"));
        favoriteRepository.delete(favorite);
    }


}

