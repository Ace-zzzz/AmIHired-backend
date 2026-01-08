package com.casey.aimihired.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.casey.aimihired.DTO.ChangePasswordDTO;
import com.casey.aimihired.DTO.UpdateUserNameDTO;
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
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match!");
        }

        User entity = new User();
        UserDTO response = new UserDTO();

        // HASH PASSWORD
        String hashedPassword = encoder.encode(user.getPassword());

        // SET USER FIELDS
        entity.setEmail(user.getEmail().trim());
        entity.setUserName(user.getUserName().trim());
        entity.setPassword(hashedPassword);
        
        // SAVE
        repo.save(entity);

        // CREATE JSON RESPONSE
        response.setEmail(entity.getEmail());
        response.setUserName(entity.getUserName());
        response.setResponse("Successfully Created");

        return response;
    }

    // CHANGE USER PASSWORD
    @Override
    public ChangePasswordDTO changePassword(Long userId, ChangePasswordDTO changePasswordRequest) {
        // FETCH USER FROM DB
        User user = repo.findById(userId).orElseThrow(
            () -> new IllegalArgumentException("User not found")
        );

        // VALIDATES IF CURRENT PASSWORD IS CORRECT
        if (!encoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current Password is wrong");
        }

        // VALIDATES IF NEW PASSWORD AND CONFIRM PASSWORD IS MATCH
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("Mismatch Password");
        }

        // UPDATE THE PASSWORD
        user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
        repo.save(user);
        
        ChangePasswordDTO response = new ChangePasswordDTO("Successfully Changed Password");

        return response;
    }

    // UPDATE USERNAME
    @Override
    public UpdateUserNameDTO updateUserName(Long userId, UpdateUserNameDTO newUsernameRequest) {
        // FETCH USER FROM DB
        User user = repo.findById(userId).orElseThrow(
            () -> new IllegalArgumentException("User not found")
        );

        // UPDATE USERNAME
        user.setUserName(newUsernameRequest.getUserName().trim());
        repo.save(user);
        
        UpdateUserNameDTO response = new UpdateUserNameDTO("Successfully updated Username");

        return response;
    }
}
