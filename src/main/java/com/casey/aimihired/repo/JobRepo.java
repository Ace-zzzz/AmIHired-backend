package com.casey.aimihired.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.casey.aimihired.models.Job_application.Job;

public interface JobRepo extends JpaRepository<Job, Long>{
    @EntityGraph(attributePaths = {"user"}) // N+1 KILLER
    List<Job> findByUserUsername(String username);
    Optional<Job> findByIdAndUserUsername(Long id, String username);
}
