//package com.parkrangers.parkquest_backend.model;
//
//import com.parkrangers.parkquest_backend.model.response.Park;
//import jakarta.persistence.*;
//
//@Entity
//@Table(name =  "favorites")
//public class Favorite {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name="user_id", nullable = false)
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name="park_id", nullable = false)
//    private Park park;
//
//    public Favorite(Long id, User user, Park park) {
//        this.id = id;
//        this.user = user;
//        this.park = park;
//    }
//    public Favorite() {}
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Park getPark() {
//        return park;
//    }
//
//    public void setPark(Park park) {
//        this.park = park;
//    }
//}
