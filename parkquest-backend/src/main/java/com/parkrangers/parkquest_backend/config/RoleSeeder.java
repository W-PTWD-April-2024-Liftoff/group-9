package com.parkrangers.parkquest_backend.config;

import com.parkrangers.parkquest_backend.model.Role;
import com.parkrangers.parkquest_backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByName("ROLE_USER") == null) {
            roleRepository.save(new Role(null, "ROLE_USER"));
        }

        if (roleRepository.findByName("ROLE_ADMIN") == null) {
            roleRepository.save(new Role(null, "ROLE_ADMIN"));
        }
    }
}
