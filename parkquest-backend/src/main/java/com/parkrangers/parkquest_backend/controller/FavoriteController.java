package com.parkrangers.parkquest_backend.controller;

import com.parkrangers.parkquest_backend.model.Favorite;
import com.parkrangers.parkquest_backend.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "http://localhost:5173")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/{userId}")
    public List<Favorite> getUserFavorites(@PathVariable Long userId) {
        return favoriteService.getFavoritesByUser(userId);
    }

    @PostMapping
    public ResponseEntity<Favorite> addFavorite(
            @RequestParam Long userId,
            @RequestParam Long parkId // Updated parameter name to `parkId` for clarity
    ) {
        return ResponseEntity.ok(favoriteService.addFavorite(userId, parkId));
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFavorite(
            @RequestParam Long userId,
            @RequestParam Long parkId // Updated parameter name to `parkId` for clarity
    ) {
        favoriteService.deleteFavorite(userId, parkId);
        return ResponseEntity.ok().build();
    }
}

