package com.casey.aimihired.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casey.aimihired.DTO.ChangePasswordDTO;
import com.casey.aimihired.DTO.LoginDTO;
import com.casey.aimihired.DTO.UpdateUserNameDTO;
import com.casey.aimihired.DTO.UserDTO;
import com.casey.aimihired.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;


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

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@Valid @RequestBody LoginDTO LoginDTO) {
        LoginDTO response = service.login(LoginDTO);

        return ResponseEntity.ok(response);
    }
    

    @PutMapping("/update-password")
    public ResponseEntity<ChangePasswordDTO> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordRequest) {
        Long id = 2L;

        ChangePasswordDTO response = service.changePassword(id, changePasswordRequest);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-username")
    public ResponseEntity<UpdateUserNameDTO> updateUserName(@Valid @RequestBody UpdateUserNameDTO newUsernameRequest) {
        Long id = 2L;
        
        UpdateUserNameDTO response = service.updateUserName(id, newUsernameRequest);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/try")
    public String tryLang() {
        return "JWT WORKS!";
    }
    

}
