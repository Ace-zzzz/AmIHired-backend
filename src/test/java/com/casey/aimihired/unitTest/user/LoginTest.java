package com.casey.aimihired.unitTest.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.casey.aimihired.DTO.LoginDTO;
import com.casey.aimihired.impl.UserImpl;
import com.casey.aimihired.security.JwtUtils;

@ExtendWith(MockitoExtension.class)
public class LoginTest {
    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private UserImpl userService;

    @Test
    void login_shouldReturnJWT_whenTheresNoException() {
        // ARRANGE
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserName("validUsername");
        loginDTO.setPassword("validPassword123");


        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(loginDTO.getUserName());

        /**
         * MOCK AUTHENTICATION MANAGER CALL 
         * TO SIMULATE AUTHENTICATION
         **/
        when(authManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword())
        )).thenReturn(auth);

        /**
         * MOCK JWT UTILS CALL 
         * TO SIMULATE TOKEN GENERATION
         **/
        when(jwtUtils.generateToken(loginDTO.getUserName())).thenReturn("xxxxx.yyyyy.zzzzz");

        // ACT
        LoginDTO response = userService.login(loginDTO);

        // ASSERT
        assertEquals("xxxxx.yyyyy.zzzzz", response.getResponse());

        /**
         * VERIFIES THAT authenticate()
         * IS CALLED EXACTLY ONCE 
         **/
        verify(authManager, times(1)).authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword())
        );

        /**
         * VERIFIES THAT generateToken()
         * IS CALLED EXACTLY ONCE 
         **/
        verify(jwtUtils, times(1)).generateToken(loginDTO.getUserName());
    }
}
