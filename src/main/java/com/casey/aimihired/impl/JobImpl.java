package com.casey.aimihired.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.casey.aimihired.DTO.Job_application.GetJobDTO;
import com.casey.aimihired.DTO.Job_application.JobDTO;
import com.casey.aimihired.models.Job_application.Job;
import com.casey.aimihired.repo.JobRepo;
import com.casey.aimihired.service.JobService;

@Service
public class JobImpl implements JobService{
    private final JobRepo repo;

    // DEPENDENCY INJECTION
    public JobImpl(JobRepo repo) {
        this.repo = repo;
    }

    // CREATES JOB ENTRY
    @Override
    @Transactional
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

    // GET ALL THE JOB
    @Override
    @Transactional(readOnly = true)
    public List<GetJobDTO> getAll() {
        return repo.findAll()
                   .stream()
                   .map(this::convertToDTO)
                   .toList();
    }

    // GET SINGLE JOB
    @Override
    @Transactional(readOnly = true)
    public GetJobDTO get(Long id) {
        /**
         * THROWS EXCEPTION
         * IF JOB NOT FOUND BY ID
         **/
        Job job = repo.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Job with id " + id + " is not found")
        );

        return convertToDTO(job);
    }

    // UPDATE JOB
    @Override
    @Transactional
    public JobDTO update(Long id, JobDTO dto) {
        /**
         * THROWS EXCEPTION
         * IF JOB NOT FOUND BY ID
         **/
        Job job = repo.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Job with id " + id + " is not found")
        );

        /**
         * STATE TRANSFER 
         * TO UPDATE FIELDS
         **/ 
        mapDtoToEntity(job, dto);

        return new JobDTO("Successfully updated");
    }

    // DELETES JOB
    @Override
    @Transactional
    public String delete(Long id) {
        Job job = repo.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Job with id " + id + " is not found")
        );

        repo.delete(job);

        return "Successfully deleted";
    }

    /**
     * CONVERTS ALL
     * THE JOB INTO DTO 
     **/
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

    // UPDATES JOB ENTITY
    private void mapDtoToEntity(Job job, JobDTO dto) {
        job.setPosition(dto.getPosition());
        job.setCompany(dto.getCompany());
        job.setWorkModel(dto.getWorkModel());
        job.setStatus(dto.getStatus());
        job.setJobURL(dto.getJobURL());
    }
}
