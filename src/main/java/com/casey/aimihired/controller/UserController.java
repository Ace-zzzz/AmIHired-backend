package com.casey.aimihired.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casey.aimihired.DTO.UserDTO;
import com.casey.aimihired.models.User;
import com.casey.aimihired.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;

    // DEPENDENCY INJECTION
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserDTO user) {
        User createdUser = service.storeUser(user);
        
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    } 
}
