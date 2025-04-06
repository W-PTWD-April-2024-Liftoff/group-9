package com.parkrangers.parkquest_backend.service;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.parkrangers.parkquest_backend.model.Role;
import com.parkrangers.parkquest_backend.model.User;
import com.parkrangers.parkquest_backend.repository.RoleRepository;
import com.parkrangers.parkquest_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    public User registerOrLoginUser(String email, String username, String password) {
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new IllegalArgumentException("Invalid password");
            }
            return user;
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        return userRepository.save(newUser);
    }

    private static final String GOOGLE_CLIENT_ID = "431740330929-ojmhr2kpqa7ocfbfcte5s396mrr0l6hu.apps.googleusercontent.com";

    public User loginWithGoogle(String token) {
        try {
            GoogleIdToken googleIdToken = verifyGoogleToken(token);
            if (googleIdToken == null) {
                throw new IllegalArgumentException("Invalid Google token");
            }

            GoogleIdToken.Payload idTokenPayload = googleIdToken.getPayload();
            return getOrCreateUser(idTokenPayload);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to authenticate with Google", e);
        }
    }

    private GoogleIdToken verifyGoogleToken(String token) throws Exception {
        GoogleIdTokenVerifier verifier = initializeGoogleVerifier();
        return verifier.verify(token);
    }

    private GoogleIdTokenVerifier initializeGoogleVerifier() {
        com.google.api.client.json.JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        HttpTransport httpTransport = new NetHttpTransport();
        return new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)
                .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                .build();
    }

    private User getOrCreateUser(GoogleIdToken.Payload payload) {
        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String googleId = payload.getSubject();

        return userRepository.findByEmail(email)
                .orElseGet(() -> registerNewGoogleUser(email, name, googleId));
    }

    private User registerNewGoogleUser(String email, String name, String googleId) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(name);
        newUser.setGoogleId(googleId);
        newUser.setPassword(null); // No password required for Google accounts

        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            throw new IllegalStateException("Default role not found. Please seed roles in the database.");
        }
        newUser.setRoles(Set.of(userRole));
        return userRepository.save(newUser);
    }


    public User registerUser(String email, String username, String password) {
        // Check if email or username already exists
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

        // Assign default role
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            throw new IllegalStateException("Default role not found. Please seed roles in the database.");
        }
        newUser.setRoles(Set.of(userRole));

        return userRepository.save(newUser);
    }
}