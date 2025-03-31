package com.parkrangers.parkquest_backend.service;

import com.parkrangers.parkquest_backend.model.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // Retrieve the authenticated user from the SecurityContext
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // Assuming the user details are stored in the principal object
            User user = (User) authentication.getPrincipal();
            return user;
        } else {
            // Handle the case where there is no authenticated user (e.g., throw an exception or return a default user)
            throw new RuntimeException("No authenticated user found");
        }
    }


}
