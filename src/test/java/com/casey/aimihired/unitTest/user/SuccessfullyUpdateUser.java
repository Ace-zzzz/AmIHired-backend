package com.casey.aimihired.unitTest.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
public class SuccessfullyUpdateUser {
    @Mock
    private UserRepo repo;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserImpl service;

    @Test
    void channgeUserPassword() {
        // ARRANGE
        Long id = 1L;
        User user = new User();
        user.setId(id);
        user.setPassword("old_password123");

        ChangePasswordDTO dto = new ChangePasswordDTO("");
        dto.setCurrentPassword("oldpassword123");
        dto.setNewPassword("newPassword");
        dto.setConfirmPassword("newPassword");

        // ACT
        when(repo.findById(id)).thenReturn(Optional.of(user));
        when(encoder.matches(dto.getCurrentPassword(), user.getPassword())).thenReturn(true);
        when(encoder.encode("newPassword")).thenReturn("new_password123");

        // ASSERT
        ChangePasswordDTO response = service.changePassword(id, dto);

        assertNotEquals(dto.getNewPassword(), "newPassword123");
        assertEquals("Successfully Changed Password", response.getResponse());
    }

    @Test
    void updateUserName() {
        // ARRANGE
        Long id = 1L;
        User user = new User();
        user.setId(id);
        user.setUserName("ace_12345");

        UpdateUserNameDTO dto = new UpdateUserNameDTO("");
        dto.setUserName("casey_123");

        // ACT
        when(repo.findById(id)).thenReturn(Optional.of(user));

        // ASSERT
        UpdateUserNameDTO response = service.updateUserName(id, dto);

        assertEquals("Successfully updated Username", response.getResponse());

        verify(repo, times(1)).save(user);
    }
}
