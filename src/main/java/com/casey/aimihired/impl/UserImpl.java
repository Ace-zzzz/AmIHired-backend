package com.casey.aimihired.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.casey.aimihired.DTO.UserDTO;
import com.casey.aimihired.models.User;
import com.casey.aimihired.repo.UserRepo;
import com.casey.aimihired.service.UserService;

@Service
public class UserImpl implements UserService {
    private final UserRepo repo;
    private final PasswordEncoder encoder;

    // DEPENDENCY INJECTION
    public UserImpl(UserRepo repo, PasswordEncoder encoder) {
        this.repo    = repo;
        this.encoder = encoder;
    }

    // STORE USER TO DB
    @Override
    public UserDTO storeUser(UserDTO user) {
        User object = new User();
        UserDTO response = new UserDTO();
        
        // HASH PASSWORD
        String rawPassword = user.getPassword();
        String hashedPassword = encoder.encode(rawPassword);

        // SET USER FIELDS
        object.setEmail(user.getEmail());
        object.setUserName(user.getUserName());
        object.setPassword(hashedPassword);
        
        // SAVE
        repo.save(object);

        // CREATE JSON RESPONSE
        response.setEmail(object.getEmail());
        response.setUserName(object.getUserName());
        response.setResponse("Successfully Created");

        return response;
    }
}
