package com.parkrangers.parkquest_backend.repository;

import com.parkrangers.parkquest_backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleUser);
}