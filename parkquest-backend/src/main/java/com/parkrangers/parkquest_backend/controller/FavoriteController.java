package com.parkrangers.parkquest_backend.controller;

import com.parkrangers.parkquest_backend.model.response.Park;
import com.parkrangers.parkquest_backend.service.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/favorites")
@CrossOrigin(origins = "http://localhost:3000")
public class FavoriteController {
    @Autowired
    private ParkService parkService;

    @GetMapping("/{userId}")
    public List<Park> getUserFavorites(@PathVariable Long userId) {
        return parkService.getFavoritesByUser(userId);
    }

    @PostMapping
    public ResponseEntity<Park> addFavorite(
            @RequestParam Long userId,
            @RequestParam String parkCode,
            @RequestParam String fullName,
            @RequestParam String description,
            @RequestParam String noteToSelf
    ){
        Park park = parkService.addFavorite(userId, parkCode, fullName, description, noteToSelf);
        return ResponseEntity.ok(park); // Return success
    }

    @PutMapping("/note")
    public ResponseEntity<Park> updateNoteToSelf(
            @RequestBody Map<String, Object> payload
    ) {
        Long userId = Long.valueOf(payload.get("userId").toString());
        Long parkId = Long.valueOf(payload.get("parkId").toString());
        String noteToSelf = payload.get("noteToSelf").toString();

        Park updatedPark = parkService.updateNoteToSelf(userId, parkId, noteToSelf);
        return ResponseEntity.ok(updatedPark);
    }


    @DeleteMapping
    public ResponseEntity<Void> removeFavorite(@RequestParam Long userId, @RequestParam String parkCode) {
        parkService.deleteFavorite(userId, parkCode);
        return ResponseEntity.ok().build();
    }


}
