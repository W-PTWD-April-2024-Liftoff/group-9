package com.parkrangers.parkquest_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

//    @Column(name = "googleId", nullable = true, unique = true)
//    private String googleId;

    @Column(name = "username", nullable = true, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    private boolean IsAdmin;

    @ManyToMany(fetch = FetchType.EAGER)  // Eager fetch ensures roles are loaded immediately with the user
    @JoinTable(
            name = "user_roles", // Intermediate table for user-role relationship
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    private Set<Role> roles;

    public User(Long userId) {

    }

//    public String getGoogleId() {
//        return googleId;
//    }
//
//    public void setGoogleId(String googleId) {
//        this.googleId = googleId;
//    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public void setPassword(String password) {
        this.password = password;
    }

    //public void setAdmin(Boolean IsAdmin) {IsAdmin = Boolean.TRUE;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Map roles to Spring Security GrantedAuthority objects
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Set to true unless you want to add functionality for expiring accounts
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Set to true unless you want to lock accounts
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Set to true unless you want passwords to expire
    }

    @Override
    public boolean isEnabled() {
        return true; // Set to false if users need to be explicitly enabled (e.g., via email verification)
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
