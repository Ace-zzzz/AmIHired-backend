package com.casey.aimihired.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casey.aimihired.models.User;

public interface UserRepo extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
}
