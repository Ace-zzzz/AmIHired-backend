package com.casey.aimihired.service;

import com.casey.aimihired.DTO.ChangePasswordDTO;
import com.casey.aimihired.DTO.LoginDTO;
import com.casey.aimihired.DTO.UpdateUserNameDTO;
import com.casey.aimihired.DTO.UserDTO;

public interface UserService {
    public UserDTO storeUser(UserDTO user); 
    public ChangePasswordDTO changePassword(Long userId, ChangePasswordDTO changePasswordRequest);
    public UpdateUserNameDTO updateUserName(Long userId, UpdateUserNameDTO newUsernameRequest);
    public LoginDTO login(LoginDTO loginDTO);
}
