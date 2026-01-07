package com.casey.aimihired.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casey.aimihired.DTO.ChangePasswordDTO;
import com.casey.aimihired.DTO.UpdateUserNameDTO;
import com.casey.aimihired.DTO.UserDTO;
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
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO user) {
        UserDTO createdUser = service.storeUser(user);
        
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    } 

    @PostMapping("/change-password")
    public ResponseEntity<ChangePasswordDTO> changePassword(@Valid @RequestBody ChangePasswordDTO newPassword) {
        Long id = 1L;

        ChangePasswordDTO response = service.changePassword(id, newPassword);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/update-username")
    public ResponseEntity<UpdateUserNameDTO> updateUserName(@Valid @RequestBody UpdateUserNameDTO newUserName) {
        Long id = 1L;
        
        UpdateUserNameDTO response = service.updateUserName(id, newUserName);

        return ResponseEntity.ok(response);
    } 
    
}
