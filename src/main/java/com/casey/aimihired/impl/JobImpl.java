package com.casey.aimihired.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.casey.aimihired.DTO.Job_application.GetJobDTO;
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

    @Override
    public List<GetJobDTO> getAll() {
        return repo.findAll()
                   .stream()
                   .map(this::convertToDTO)
                   .toList();
    }

    private GetJobDTO convertToDTO(Job job) {
        GetJobDTO dto = new GetJobDTO();
        dto.setId(job.getId());
        dto.setPosition(job.getPosition());
        dto.setCompany(job.getCompany());
        dto.setWorkModel(job.getWorkModel());
        dto.setStatus(job.getStatus());
        dto.setJobURL(job.getJobURL());

        return dto;
    }
}
