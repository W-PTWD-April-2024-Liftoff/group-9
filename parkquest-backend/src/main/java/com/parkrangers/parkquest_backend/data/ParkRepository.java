package com.parkrangers.parkquest_backend.data;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.parkrangers.parkquest_backend.models.Park;

@Repository
public interface ParkRepository extends CrudRepository<Park, Integer> {
}