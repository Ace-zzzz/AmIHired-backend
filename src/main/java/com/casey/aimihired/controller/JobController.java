package com.casey.aimihired.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casey.aimihired.DTO.Job_application.JobDTO;
import com.casey.aimihired.service.JobService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<JobDTO> create(@RequestBody JobDTO dto) {
        JobDTO response = service.create(dto);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
}
