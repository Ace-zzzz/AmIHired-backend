package com.casey.aimihired.service;

import com.casey.aimihired.DTO.user.ChangePasswordDTO;
import com.casey.aimihired.DTO.user.LoginDTO;
import com.casey.aimihired.DTO.user.UpdateUserNameDTO;
import com.casey.aimihired.DTO.user.UserDTO;
import com.casey.aimihired.util.ApiResponse;

public interface UserService {
    public ApiResponse store(UserDTO user); 
    public ApiResponse changePassword(String username, ChangePasswordDTO changePasswordRequest);
    public ApiResponse updateUserName(String username, UpdateUserNameDTO newUsernameRequest);
    public ApiResponse login(LoginDTO loginDTO);
}
