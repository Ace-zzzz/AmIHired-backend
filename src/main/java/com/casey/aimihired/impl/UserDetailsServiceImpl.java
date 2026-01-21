package com.casey.aimihired.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.casey.aimihired.models.User;
import com.casey.aimihired.repo.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    private final UserRepo repo;

    public UserDetailsServiceImpl(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException("User not found")
        );

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(), 
            user.getPassword(), 
            new java.util.ArrayList<>()
    );
    }
}
