//package com.parkrangers.parkquest_backend.service;
//
//import com.parkrangers.parkquest_backend.repository.FavoriteRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class FavoriteService {
//    @Autowired
//    private FavoriteRepository favoriteRepository;
//
//    public List<Favorite> getFavoritesByUser(Long userId) {
//        return favoriteRepository.findByUserId(userId);
//    }
//
//    public Favorite addFavorite(Long userId, Long parkId) {
//        if (favoriteRepository.findByUserIdAndParkId(userId, parkId).isPresent()) {
//            throw new RuntimeException("Favorite already exists");
//        }
//
//        Favorite favorite = new Favorite();
//
//        favorite.setUser(new User(userId)); //assuming user exists
//        favorite.setPark(new Park(parkId)); //assuming park exits
//        return favoriteRepository.save(favorite);
//    }
//
//    public void deleteFavorite(Long userId, Long parkId) {
//        favoriteRepository.findByUserIdAndParkId(userId, parkId)
//                .ifPresent(favoriteRepository::delete);
//    }
//}
