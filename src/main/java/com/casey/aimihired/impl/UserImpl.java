package com.casey.aimihired.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.casey.aimihired.DTO.user.ChangePasswordDTO;
import com.casey.aimihired.DTO.user.LoginDTO;
import com.casey.aimihired.DTO.user.UpdateUserNameDTO;
import com.casey.aimihired.DTO.user.UserDTO;
import com.casey.aimihired.models.User;
import com.casey.aimihired.repo.UserRepo;
import com.casey.aimihired.security.JwtUtils;
import com.casey.aimihired.service.UserService;
import com.casey.aimihired.util.ApiResponse;

@Service
public class UserImpl implements UserService {
    private final UserRepo repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;

    // DEPENDENCY INJECTION
    public UserImpl(UserRepo repo, PasswordEncoder encoder, AuthenticationManager authManager, JwtUtils jwtUtils) {
        this.repo    = repo;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwtUtils = jwtUtils;
    }

    // STORE USER TO DB
    @Override
    @Transactional
    public ApiResponse store(UserDTO user) {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match!");
        }

        User entity = new User();

        // HASH PASSWORD
        String hashedPassword = encoder.encode(user.getPassword());

        // SET USER FIELDS
        entity.setEmail(user.getEmail().trim());
        entity.setUsername(user.getUsername().trim());
        entity.setPassword(hashedPassword);
        
        // SAVE
        repo.save(entity);

        return new ApiResponse("Successfully Register", true);
    }

    // LOGIN USER
    @Override
    public ApiResponse login(LoginDTO loginDTO) {
        /**
         * AUTHENTICATE USER VIA
         * USERNAME AND PASSWORD
         **/ 
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );

        // GENERATES JWT
        String token = jwtUtils.generateToken(auth.getName());

        return new ApiResponse(token, true);
    }

    // CHANGE USER PASSWORD
    @Override
    @Transactional
    public ApiResponse changePassword(Long userId, ChangePasswordDTO changePasswordRequest) {
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
            throw new IllegalArgumentException("Passwords do not match!");
        }

        // UPDATE THE PASSWORD
        user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
        
        return new ApiResponse("Successfully Changed Password", true);
    }

    // UPDATE USERNAME
    @Override
    @Transactional
    public ApiResponse updateUserName(Long userId, UpdateUserNameDTO newUsernameRequest) {
        // FETCH USER FROM DB
        User user = repo.findById(userId).orElseThrow(
            () -> new IllegalArgumentException("User not found")
        );

        // UPDATE USERNAME
        user.setUsername(newUsernameRequest.getUsername().trim());
        
        return new ApiResponse("Successfully updated Username", true);
    }
}
