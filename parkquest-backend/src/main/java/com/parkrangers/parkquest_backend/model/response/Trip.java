package com.parkrangers.parkquest_backend.model.response;

import com.parkrangers.parkquest_backend.model.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;


}
