package com.casey.aimihired.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.casey.aimihired.DTO.ChangePasswordDTO;
import com.casey.aimihired.DTO.LoginDTO;
import com.casey.aimihired.DTO.UpdateUserNameDTO;
import com.casey.aimihired.DTO.UserDTO;
import com.casey.aimihired.models.User;
import com.casey.aimihired.repo.UserRepo;
import com.casey.aimihired.security.JwtUtils;
import com.casey.aimihired.service.UserService;

@Service
public class UserImpl implements UserService {
    private final UserRepo repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManger;
    private final JwtUtils jwtUtils;

    // DEPENDENCY INJECTION
    public UserImpl(UserRepo repo, PasswordEncoder encoder, AuthenticationManager authManger, JwtUtils jwtUtils) {
        this.repo    = repo;
        this.encoder = encoder;
        this.authManger = authManger;
        this.jwtUtils = jwtUtils;
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

    // LOGIN USER
    @Override
    public LoginDTO login(LoginDTO loginDTO) {
        /**
         * AUTHENTICATE USER VIA
         * USERNAME AND PASSWORD
         **/ 
        Authentication auth = authManger.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword())
        );

        // GENERATES JWT
        String token = jwtUtils.generateToken(auth.getName());

        /**
         * INSTANTIATE LOGIN 
         * DTO FOR RESPONSE
         **/ 
        LoginDTO response = new LoginDTO(token);

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
            throw new IllegalArgumentException("Passwords do not match!");
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
