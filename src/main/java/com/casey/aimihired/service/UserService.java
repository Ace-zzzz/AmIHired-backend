package com.casey.aimihired.service;

import com.casey.aimihired.DTO.UserDTO;
import com.casey.aimihired.models.User;

public interface UserService {
    public User storeUser(UserDTO user); 
}
