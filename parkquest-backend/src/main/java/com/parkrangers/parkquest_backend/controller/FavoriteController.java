package com.parkrangers.parkquest_backend.controller;
import com.parkrangers.parkquest_backend.model.Favorite;
import com.parkrangers.parkquest_backend.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
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
            @RequestParam String parkCode,
            @RequestParam String fullName
    ){
        Favorite favorite = favoriteService.addFavorite(userId, parkCode, fullName);
        return ResponseEntity.ok(favorite); // Return success
    }

@DeleteMapping
    public ResponseEntity<Void> removeFavorite(@RequestParam Long userId, @RequestParam String parkCode) {
        favoriteService.deleteFavorite(userId, parkCode);
        return ResponseEntity.ok().build();
    }


}
