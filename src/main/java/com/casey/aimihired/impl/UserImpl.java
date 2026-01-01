package com.casey.aimihired.impl;

import org.springframework.stereotype.Service;

import com.casey.aimihired.DTO.UserDTO;
import com.casey.aimihired.models.User;
import com.casey.aimihired.repo.UserRepo;
import com.casey.aimihired.service.UserService;

@Service
public class UserImpl implements UserService {
    private final UserRepo repo;

    // DEPENDENCY INJECTION
    public UserImpl(UserRepo repo) {
        this.repo = repo;
    }

    // STORE USER TO DB
    @Override
    public UserDTO storeUser(UserDTO user) {
        User object = new User();
        UserDTO response = new UserDTO();
        
        object.setEmail(user.getEmail());
        object.setUserName(user.getUserName());
        object.setPassword(user.getPassword());
        
        repo.save(object);

        response.setEmail(object.getEmail());
        response.setUserName(object.getUserName());
        response.setResponse("Successfully Created");

        return response;
    }
}
