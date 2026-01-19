package com.casey.aimihired.impl;

import org.springframework.stereotype.Service;

import com.casey.aimihired.DTO.Job_application.JobDTO;
import com.casey.aimihired.models.Job_application.Job;
import com.casey.aimihired.repo.JobRepo;
import com.casey.aimihired.service.JobService;

@Service
public class JobImpl implements JobService{
    private final JobRepo repo;

    public JobImpl(JobRepo repo) {
        this.repo = repo;
    }

    // CREATES JOB ENTRY
    @Override
    public JobDTO create(JobDTO dto) {
        // CREATE NEW JOB ENTITY
        Job entity = new Job();
        entity.setPosition(dto.getPosition());
        entity.setCompany(dto.getCompany());
        entity.setWorkModel(dto.getWorkModel());
        entity.setStatus(dto.getStatus());
        entity.setJobURL(dto.getJobURL());

        // SAVE THE ENTITY ON DATABASE 
        repo.save(entity);

        // RETURN A DTO RESPONSE
        JobDTO response = new JobDTO("Successfully created");

        return response;
    }
}
