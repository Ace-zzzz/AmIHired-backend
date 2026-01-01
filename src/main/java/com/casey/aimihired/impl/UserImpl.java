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
    public User storeUser(UserDTO user) {
        try {
            User object = new User();
            
            object.setEmail(user.getEmail());
            object.setUserName(user.getUserName());
            object.setPassword(user.getPassword());

            return repo.save(object);
        } 
        catch(Exception error) {
            System.out.println("Huli ka " + error.getMessage());

            return null;
        }
    }
}
