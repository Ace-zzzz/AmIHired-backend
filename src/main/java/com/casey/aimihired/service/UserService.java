package com.casey.aimihired.service;

import com.casey.aimihired.DTO.ChangePasswordDTO;
import com.casey.aimihired.DTO.UserDTO;

public interface UserService {
    public UserDTO storeUser(UserDTO user); 
    public ChangePasswordDTO changePassword(Long userId, ChangePasswordDTO newPassword);
}
