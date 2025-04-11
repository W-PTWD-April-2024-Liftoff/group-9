package com.parkrangers.parkquest_backend.service;

import com.parkrangers.parkquest_backend.model.Role;
import com.parkrangers.parkquest_backend.model.User;
import com.parkrangers.parkquest_backend.repository.RoleRepository;
import com.parkrangers.parkquest_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public User loginUser(String email, String password) {
        // Find existing user by email
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isEmpty()) {
            throw new IllegalArgumentException("User with the given email does not exist.");
        }

        User user = existingUser.get();

        // Validate the given password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        // Return the logged-in user's details
        return user;
    }

    public User registerUser(String email, String username, String password, String role) {
        //check if email already exists
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already taken.");
        }

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username is already taken.");
        }
        // Register new user
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));

        // Assign role to the user
        Role assignedRole = roleRepository.findByName(role);
        if (assignedRole == null) {
            throw new IllegalStateException("Role not found.");
        }
        newUser.setRoles(Set.of(assignedRole));

        return userRepository.save(newUser);
    }

    public User updateProfileWithRole(Long userId, String email, String currentPassword, String newPassword, String role) {
        System.out.println("Finding user with ID: " + userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + userId));

        // Email Update
        if (email == null || email.isBlank()) {
            System.out.println("Email provided is null or blank; skipping update.");
        } else if (!email.equals(user.getEmail())) {
            if (userRepository.existsByEmail(email)) {
                throw new IllegalArgumentException("Email is already in use: " + email);
            }
            System.out.println("Updating email to: " + email);
            user.setEmail(email);
        }

        // Password Update
        if (newPassword != null && !newPassword.isBlank()) {
            if (currentPassword == null || currentPassword.isBlank()) {
                throw new IllegalArgumentException("Current password is missing for password update.");
            }
            if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                throw new IllegalArgumentException("Current password is incorrect.");
            }
            System.out.println("Updating password for user.");
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        // Role Update
        if (role != null) {
            System.out.println("Looking up role: " + role);
            Role roleToUpdate = roleRepository.findByName(role);
            if (roleToUpdate == null) {
                throw new IllegalStateException("Role not found: " + role);
            }
            System.out.println("Assigning role: " + roleToUpdate.getName());

            // Use HashSet to create a modifiable collection
            Set<Role> modifiableRoles = new HashSet<>();
            modifiableRoles.add(roleToUpdate);
            user.setRoles(modifiableRoles);
        }


        System.out.println("Attempting to save updated user: " + user);
        try {
            System.out.println("Saving user with roles: " + user.getRoles());
            User updatedUser = userRepository.save(user);
            System.out.println("User saved successfully: " + updatedUser);
            return updatedUser;
        } catch (Exception e) {
            System.err.println("Error occurred while saving user: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw for proper handling in the controller
        }

    }
}