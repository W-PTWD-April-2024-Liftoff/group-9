package com.parkrangers.parkquest_backend.controller;

import com.parkrangers.parkquest_backend.model.ApiResponse;
import com.parkrangers.parkquest_backend.model.User;
import com.parkrangers.parkquest_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173") // Frontend's origin domain
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> loginOrRegister(@RequestBody LoginRequest request) {
        try {
            User user = userService.registerOrLoginUser(
                    request.getEmail(),
                    request.getName(),
                    request.getPassword()
            );
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse(false, ex.getMessage())
            );
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest request) {
        try {
            // Call the service to register a user
            User user = userService.registerUser(request.getEmail(), request.getUsername(), request.getPassword());
            return ResponseEntity.ok(
                    new ApiResponse(true, "User registered successfully!")
            );
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse(false, ex.getMessage())
            );
        }
    }

    @PostMapping("/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody GoogleLoginRequest googleLoginRequest) {
        try {
            // Delegate to UserService to handle Google login
            User user = userService.loginWithGoogle(googleLoginRequest.getToken());
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, ex.getMessage()));
        }
    }

    static class GoogleLoginRequest {
        private String token; // Google OAuth token

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    static class LoginRequest {
        private String email;
        private String name; // Optional for existing users
        private String password;

        // Getters and setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    static class SignupRequest {
        private String email;
        private String username;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}