package com.casey.aimihired.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casey.aimihired.DTO.user.ChangePasswordDTO;
import com.casey.aimihired.DTO.user.LoginDTO;
import com.casey.aimihired.DTO.user.UpdateUserNameDTO;
import com.casey.aimihired.DTO.user.UserDTO;
import com.casey.aimihired.service.UserService;
import com.casey.aimihired.util.ApiResponse;

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
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody UserDTO user) {
        ApiResponse userObject = service.store(user);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(userObject);
    } 

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginDTO loginDTO) {
        ApiResponse response = service.login(loginDTO);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-password")
    public ResponseEntity<ApiResponse> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordRequest) {
        Long id = 2L;

        ApiResponse response = service.changePassword(id, changePasswordRequest);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-username")
    public ResponseEntity<ApiResponse> updateUserName(@Valid @RequestBody UpdateUserNameDTO newUsernameRequest) {
        Long id = 2L;
        
        ApiResponse response = service.updateUserName(id, newUsernameRequest);

        return ResponseEntity.ok(response);
    }
}
