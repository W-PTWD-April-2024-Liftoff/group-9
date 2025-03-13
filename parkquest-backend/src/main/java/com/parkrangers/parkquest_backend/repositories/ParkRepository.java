package com.parkrangers.parkquest_backend.repositories;

import com.parkrangers.parkquest_backend.models.Park;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkRepository extends CrudRepository<Park, Integer> {
}
