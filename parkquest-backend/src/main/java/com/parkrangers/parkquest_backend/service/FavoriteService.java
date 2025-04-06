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

    public List<Favorite> getFavoritesByUser(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }

    public Favorite addFavorite(Long userId, Long parkId) {
        // Debugging: Log userId and parkId
        System.out.println("AddFavorite: userId=" + userId + ", parkId=" + parkId);

        if (favoriteRepository.findByUserIdAndPark_ParkId(userId, parkId).isPresent()) {
            throw new RuntimeException("Favorite already exists");
        }

        Favorite favorite = new Favorite();
        favorite.setUser(new User(userId)); // Ensure valid user
        favorite.setPark(new Park(parkId)); // Ensure valid park
        return favoriteRepository.save(favorite);
    }


    public void deleteFavorite(Long userId, Long parkId) {
        Favorite favorite = favoriteRepository.findByUserIdAndPark_ParkId(userId, parkId)
                .orElseThrow(() -> new RuntimeException("Favorite not found"));

        favoriteRepository.delete(favorite);
    }

}

