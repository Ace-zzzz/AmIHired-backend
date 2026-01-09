package com.casey.aimihired.unitTest.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.casey.aimihired.DTO.ChangePasswordDTO;
import com.casey.aimihired.DTO.UpdateUserNameDTO;
import com.casey.aimihired.impl.UserImpl;
import com.casey.aimihired.models.User;
import com.casey.aimihired.repo.UserRepo;

@ExtendWith(MockitoExtension.class)
public class UpdateUserTest {
    @Mock
    private UserRepo repo;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserImpl userService;

    @Test
    void updateUserPassword_shouldThrowException_whenUserDidNotFound() {
        // ARRANGE
        Long requestId = 404L;

        ChangePasswordDTO changePasswordRequest = new ChangePasswordDTO();

        // ACT
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> userService.changePassword(requestId, changePasswordRequest)
        );

        /**
         * VERIFIES THAT THE PASSWORD 
         * DID NOT ENCRYPTED IN THE FIRST PLACE
         **/ 
        verifyNoInteractions(encoder);

        /**
         * VERIFIES THAT THE NEW PASSWORD
         * DID NOT SAVE ON DATABASE 
         **/
        verify(repo, times(1)).findById(requestId);
        verifyNoMoreInteractions(repo);

        // ASSERT
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void updateUserPassword_shouldThrowException_whenGivenPasswordAndActualPasswordDidNotMatch() {
        // ARRANGE
        Long userId = 1L;

        User user = new User();
        user.setId(userId);
        user.setPassword("actualPassword");

        ChangePasswordDTO changePasswordRequest = new ChangePasswordDTO();
        changePasswordRequest.setCurrentPassword("currentPassword");

        /**
         * MOCK REPOSITORY CALL 
         * TO SIMULATE THE FINDING OF USER BY ID
         **/
        when(repo.findById(userId)).thenReturn(Optional.of(user));

        // ACT
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> userService.changePassword(userId, changePasswordRequest)  
        );

        /**
         * VERIFIES THAT THE NEW PASSWORD
         * DID NOT SAVE ON DATABASE 
         **/
        verify(repo, times(1)).findById(userId);
        verifyNoMoreInteractions(repo);

        
        /**
         * VERIFIES THAT THE NEW PASSWORD
         * DID NOT REACH INTO ENCRYPTION
         **/
        verify(encoder, times(1)).matches(changePasswordRequest.getCurrentPassword(), user.getPassword());   
        verifyNoMoreInteractions(encoder);

        // ASSERT
        assertEquals("Current Password is wrong", exception.getMessage());
    }

    @Test
    void updateUserPassword_shouldThrowException_whenNewPasswordDidNotMatch() {
        // ARRANGE
        Long userId = 1L;

        User user = new User();
        user.setId(userId);
        user.setPassword("actualPassword");

        ChangePasswordDTO changePasswordRequest = new ChangePasswordDTO();
        changePasswordRequest.setCurrentPassword("actualPassword");
        changePasswordRequest.setNewPassword("newPassword");
        changePasswordRequest.setConfirmPassword("newPassword123");

        /**
         * MOCK REPOSITORY CALL 
         * TO SIMULATE THE FINDING OF USER BY ID
         **/
        when(repo.findById(user.getId())).thenReturn(Optional.of(user));

        /**
         * MOCK ENCODER CALL 
         * TO SIMULATE MATCHING OF THE PASSWORD
         **/
        when(encoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())).thenReturn(true);

        // ACT
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> userService.changePassword(userId, changePasswordRequest)  
        );

        /**
         * VERIFIES THAT THE NEW PASSWORD
         * DID NOT SAVE ON DATABASE 
         **/
        verify(repo, times(1)).findById(userId);
        verifyNoMoreInteractions(repo);

        
        /**
         * VERIFIES THAT THE NEW PASSWORD
         * DID NOT REACH INTO ENCRYPTION
         **/
        verify(encoder, times(1)).matches(changePasswordRequest.getCurrentPassword(), user.getPassword());   
        verifyNoMoreInteractions(encoder);

        // ASSERT
        assertEquals("Mismatch Password", exception.getMessage());
    }

    @Test
    void updateUserPassword_hashedAndSaveToDB_whenNoExceptionError() {
       // ARRANGE
       Long userId = 1L;

       User user = new User();
       user.setId(userId);
       user.setPassword("actualPassword");
       
       ChangePasswordDTO changePasswordRequest = new ChangePasswordDTO();
       changePasswordRequest.setCurrentPassword("actualPassword");
       changePasswordRequest.setNewPassword("newPassword");
       changePasswordRequest.setConfirmPassword("newPassword");

        /**
         * MOCK REPOSITORY CALL 
         * TO SIMULATE THE FINDING OF USER BY ID
         **/
        when(repo.findById(user.getId())).thenReturn(Optional.of(user));

        /**
         * MOCK ENCODER CALL 
         * TO SIMULATE THE ENCRYPTION OF PASSWORD
         **/
        when(encoder.encode(changePasswordRequest.getNewPassword())).thenReturn("new_hashed_password");
        
        /**
         * MOCK ENCODER CALL 
         * TO SIMULATE MATCHING OF THE PASSWORD
         **/
        when(encoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())).thenReturn(true);


       // ACT
       ChangePasswordDTO response = userService.changePassword(userId, changePasswordRequest);

       // VERIFIES THAT findById() IS CALLED ONE TIME
       verify(repo, times(1)).findById(user.getId());

       // VERIFIES THAT encode() IS CALLED ONE TIME
       verify(encoder, times(1)).encode(changePasswordRequest.getNewPassword());

       /**
        * VERIFIES THAT save() IS CALLED ONE TIME
        * AND INDEED SAVE AND UPDATE THE USER'S PASSWORD
        **/
       verify(repo, times(1)).save(user);

       // ASSERT
       assertEquals("new_hashed_password", user.getPassword());
       assertEquals("Successfully Changed Password", response.getResponse());
    }

    @Test
    void updateUsername_shouldThrowException_whenUserDidNotFound() {
        // ARRANGE
        Long userId = 404L;

        UpdateUserNameDTO newUsernameRequest = new UpdateUserNameDTO();
        
        // ACT
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, 
            () -> userService.updateUserName(userId, newUsernameRequest)
        );

        // ASSERT
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void updateUsername_shouldSaveAndUpdateUsername_whenNoExceptionError() {
        // ARRANGE
        Long userId = 1L;

        User user = new User();
        user.setId(userId);
        user.setUserName("oldUsername");

        UpdateUserNameDTO newUsernameRequest = new UpdateUserNameDTO();
        newUsernameRequest.setUserName("updatedUsername");

        /**
         * MOCK THE REPOSITORY CALL
         * TO SIMULATE THE FINDING OF USER 
         * USING ID 
         **/
        when(repo.findById(user.getId())).thenReturn(Optional.of(user));
        
        // ACT
        UpdateUserNameDTO response = userService.updateUserName(userId, newUsernameRequest);

       /**
        * VERIFIES THAT findById() IS CALLED 
        * ONLY ONE TIME
        **/
        verify(repo, times(1)).findById(user.getId());

        
       /**
        * VERIFIES THAT save() IS CALLED ONE TIME
        * AND INDEED SAVE AND UPDATE THE USER'S USERNAME
        **/
        verify(repo, times(1)).save(user);

        // ASSERT
        assertEquals(newUsernameRequest.getUserName(), user.getUserName());
        assertEquals("Successfully updated Username", response.getResponse());
    }
}
