package com.casey.aimihired.unitTest.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.casey.aimihired.models.User;
import com.casey.aimihired.DTO.user.UserDTO;
import com.casey.aimihired.impl.UserImpl;
import com.casey.aimihired.repo.UserRepo;
import com.casey.aimihired.util.ApiResponse;

@ExtendWith(MockitoExtension.class)
public class UserRegistrationTest {
    @Mock
    private UserRepo repo;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserImpl userService;

    /**
     * TEST FOR THROWING EXCEPTION
     * WHEN PASSWORD DID NOT MATCH
     * **/
    @Test
    void storeUser_shouldThrowException_whenPasswordDoNotMatch() {
        // ARRANGE 
        UserDTO userDTO = new UserDTO();

        userDTO.setPassword("password123");
        userDTO.setConfirmPassword("password1234");

        // ACT
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> userService.store(userDTO)
        );

        /**
         * VERIFY THAT THE USER DIDN'T SAVE ON THE DB
         * AND PASSWORD DIDN'T GET ENCRYPTED AS IT SHOULD
         * **/
        verifyNoInteractions(encoder);
        verifyNoInteractions(repo);

        // ASSERT
        assertEquals("Passwords do not match!", exception.getMessage());
    }

    // TEST FOR SAVING THE USER TO DB
    @Test
    void storeUser_ShouldHashedPasswordAndSaveToDB() {
        // ARRANGE
        UserDTO userDTO = new UserDTO();

        userDTO.setEmail("test@gmail.com");
        userDTO.setUsername("testUsername");
        userDTO.setPassword("password123");
        userDTO.setConfirmPassword("password123");

        when(encoder.encode(userDTO.getPassword())).thenReturn("hashed_password");

        // ACT
        ApiResponse response = userService.store(userDTO);

        // VERIFIES THAT THE PASSWORD GOT ENCRYPTED
        verify(encoder, times(1)).encode(userDTO.getPassword());

        // GET THE ACTUAL USER THAT SAVED INTO DB
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        /**
         * VERIFIES THAT THE USER
         * ACTUALLY SAVE INTO DB
         * **/
        verify(repo, times(1)).save(userCaptor.capture());

        /**
         * GETS THE ACTUAL VALUE OF USER
         * THAT WAS SAVED ON DB
         * **/
        User savedUser = userCaptor.getValue();

        // ASSERT
        assertEquals("hashed_password", savedUser.getPassword());
        assertEquals("Successfully Register", response.message());
    }
}
