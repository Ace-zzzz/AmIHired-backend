package com.casey.aimihired.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casey.aimihired.DTO.Job_application.GetJobDTO;
import com.casey.aimihired.DTO.Job_application.JobDTO;
import com.casey.aimihired.service.JobService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/job-application")
public class JobController {
    private final JobService service;

    // DEPENDENCY INJECTION
    public JobController(JobService service) {
        this.service = service;
    }

    /**
     * ROUTE FOR
     * CREATING JOB ENTRY
     **/
    @PostMapping("/jobs")
    public ResponseEntity<JobDTO> create(@Valid @RequestBody JobDTO dto) {
        JobDTO response = service.create(dto);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * ROUTE FOR
     * FETCHING ALL JOBS
     **/
    @GetMapping("/jobs")
    public ResponseEntity<List<GetJobDTO>> getAll() {
        List<GetJobDTO> jobs = service.getAll();

        return ResponseEntity.ok(jobs);
    }

    /**
     * ROUTE FOR
     * FETCHING SIMGLE JOBS
     **/
    @GetMapping("/jobs/{id}")
    public ResponseEntity<GetJobDTO> get(@PathVariable Long id) {
        GetJobDTO job = service.get(id);

        return ResponseEntity.ok(job);
    }
        
}
