package com.parkrangers.parkquest_backend.controller;

import com.parkrangers.parkquest_backend.model.ApiResponse;
import com.parkrangers.parkquest_backend.model.User;
import com.parkrangers.parkquest_backend.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173") // Frontend's origin domain
public class AuthController {

    @Autowired
    private UserService userService;

    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            String password = body.get("password");

            if (email == null || password == null) {
                throw new IllegalArgumentException("Email and password must be provided.");
            }
            // Validate user credentials
            User user = userService.loginUser(email, password);

            // Generate JWT token
            String token = Jwts.builder()
                    .setSubject(user.getEmail()) // Payload: user email
                    .claim("username", user.getUsername()) // Additional claims: username
                    .claim("roles", user.getRoles()) // Additional claims: roles
                    .setIssuedAt(new Date()) // Issue Date
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24-hour expiration
                    .signWith(secretKey) // Sign the token with the secret key
                    .compact();

            // Check if user has 'ROLE_ADMIN'
            boolean isAdmin = user.getRoles().stream()
                    .anyMatch(role -> role.getName().equalsIgnoreCase("ROLE_ADMIN"));

            // Return token, user info, and isAdmin status
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("userId", user.getUserId());
            response.put("username", user.getUsername());
            response.put("email", user.getEmail());
            response.put("isAdmin", isAdmin); // 👈 Added isAdmin flag

            return ResponseEntity.ok().body(response); // Send token and user data

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            String username = body.get("username");
            String password = body.get("password");
            String role = body.getOrDefault("role", "ROLE_USER");

            if (email == null || username == null || password == null) {
                throw new IllegalArgumentException("Email, username, and password must be provided.");
            }

            User user = userService.registerUser(email, username, password, role);
            return ResponseEntity.ok().body(user); // Return the registered user
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Non-static nested LoginRequest class
    class LoginRequest {
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

    // Non-static nested SignupRequest class
    class SignupRequest {
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
