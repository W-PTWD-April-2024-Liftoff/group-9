package com.parkrangers.parkquest_backend.config;

import com.parkrangers.parkquest_backend.model.Role;
import com.parkrangers.parkquest_backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RoleSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (!roleRepository.existsByName("ROLE_USER")) {
            roleRepository.save(new Role(null, "ROLE_USER"));
        }

        if (!roleRepository.existsByName("ROLE_ADMIN")) {
            roleRepository.save(new Role(null, "ROLE_ADMIN"));
        }
    }
}
