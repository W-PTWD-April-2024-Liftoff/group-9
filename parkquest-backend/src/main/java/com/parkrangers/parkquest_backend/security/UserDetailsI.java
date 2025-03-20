package com.parkrangers.parkquest_backend.security;

import com.parkrangers.parkquest_backend.models.User;
import com.parkrangers.parkquest_backend.repositories.UserRepository;
import com.parkrangers.parkquest_backend.services.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsI implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetail(user);
    }
}
