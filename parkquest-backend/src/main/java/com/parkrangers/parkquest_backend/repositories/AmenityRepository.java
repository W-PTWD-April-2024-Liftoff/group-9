package com.parkrangers.parkquest_backend.repositories;

import com.parkrangers.parkquest_backend.models.Amenity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AmenityRepository extends CrudRepository<Amenity, Integer> {
    List<Amenity> findbyParkID(Integer parkId);
}
