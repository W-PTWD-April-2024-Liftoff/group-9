//package com.parkrangers.parkquest_backend.controller;
//
////import com.parkrangers.parkquest_backend.model.Favorite;
//
//import com.parkrangers.parkquest_backend.service.FavoriteService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/favorites")
//@CrossOrigin("*")
//public class FavoriteController {
//    @Autowired
//    private FavoriteService favoriteService;
//
//    @GetMapping("/{userId}")
//    public List<Favorite> getUserFavorites(@PathVariable Long userId) {
//        return favoriteService.getFavoritesByUser(userId);
//    }
//
//    @PostMapping
//    public ResponseEntity<Favorite> addFavorite(@RequestParam Long userId, @RequestParam Long itemId) {
//        return ResponseEntity.ok(favoriteService.addFavorite(userId, itemId));
//    }
//
//    @DeleteMapping
//    public ResponseEntity<Void> removeFavorite(@RequestParam Long userId, @RequestParam Long itemId) {
//        favoriteService.deleteFavorite(userId, itemId);
//        return ResponseEntity.ok().build();
//    }
//
//
//}
