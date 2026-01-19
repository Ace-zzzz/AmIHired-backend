package com.casey.aimihired.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casey.aimihired.models.Job_application.Job;

public interface JobRepo extends JpaRepository<Job, Long>{
    
}
