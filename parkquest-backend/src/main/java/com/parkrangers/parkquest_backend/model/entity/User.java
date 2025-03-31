package com.parkrangers.parkquest_backend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // You can define roles and permissions if necessary.
        return Collections.emptyList();  // If you don't use roles, just return an empty list
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Assuming the account never expires
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Assuming the account is not locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Assuming credentials don't expire
    }

    @Override
    public boolean isEnabled() {
        return true; // Assuming the account is enabled
    }
}

