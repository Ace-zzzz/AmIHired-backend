package com.casey.aimihired.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casey.aimihired.models.User;

public interface UserRepo extends JpaRepository<User, Long>{
    
}
