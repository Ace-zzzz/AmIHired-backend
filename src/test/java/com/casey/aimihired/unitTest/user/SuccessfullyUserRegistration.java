package com.casey.aimihired.unitTest.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.casey.aimihired.DTO.UserDTO;
import com.casey.aimihired.models.User;
import com.casey.aimihired.impl.UserImpl;
import com.casey.aimihired.repo.UserRepo;

@ExtendWith(MockitoExtension.class)
public class SuccessfullyUserRegistration {
    @Mock
    private UserRepo repo;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserImpl userService;

    @Test
    void storeUser_ShouldVerifyHashedPasswordInDatabase() {
        // --- 1. ARRANGE ---
        UserDTO inputDto = new UserDTO();
        inputDto.setUserName("junior_dev");
        inputDto.setPassword("secret123");
        inputDto.setConfirmPassword("secret123");
        inputDto.setEmail("dev@test.com");

        when(encoder.encode("secret123")).thenReturn("hashed_123");

        if (!inputDto.getPassword().equals(inputDto.getConfirmPassword())) {
            throw new RuntimeException("Password is not match");
        }
        
        // --- 2. ACT ---
        userService.storeUser(inputDto);

        // --- 3. ASSERT ---
        // Create a "Net" to catch the User object
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        // Verify save was called AND catch the object that was sent to it
        verify(repo).save(userCaptor.capture());

        // Extract the "Caught" object
        User capturedUser = userCaptor.getValue();

        // The Ultimate Proof: Check if the password inside the object is the HASHED one
        assertEquals("hashed_123", capturedUser.getPassword());
        
        // Safety check: Ensure it is NOT the plain text password
        assertNotEquals("secret123", capturedUser.getPassword());
    }
}
