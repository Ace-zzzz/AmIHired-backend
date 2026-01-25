package com.casey.aimihired.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casey.aimihired.DTO.Job_application.GetJobDTO;
import com.casey.aimihired.DTO.Job_application.JobDTO;
import com.casey.aimihired.service.JobService;
import com.casey.aimihired.util.ApiResponse;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody JobDTO dto, Authentication auth) {
        ApiResponse response = service.create(dto, auth.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * ROUTE FOR
     * FETCHING ALL JOBS
     **/
    @GetMapping("/jobs")
    public ResponseEntity<List<GetJobDTO>> getAll(Authentication auth) {
        List<GetJobDTO> jobs = service.getAll(auth.getName());

        return ResponseEntity.ok(jobs);
    }

    /**
     * ROUTE FOR
     * FETCHING SINGLE JOBS
     **/
    @GetMapping("/jobs/{id}")
    public ResponseEntity<GetJobDTO> get(@PathVariable Long id, Authentication auth) {
        GetJobDTO job = service.get(id, auth.getName());

        return ResponseEntity.ok(job);
    }

    /**
     * ROUTE FOR
     * UPDATING JOB
     **/
    @PutMapping("/jobs/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @Valid @RequestBody JobDTO dto, Authentication auth) {
        ApiResponse response = service.update(id, dto, auth.getName());

        return ResponseEntity.ok(response);
    }

    /**
     * ROUTE FOR
     * DELETING JOB ENTITY
     **/
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id, Authentication auth) {
        ApiResponse response = service.delete(id, auth.getName());

        return ResponseEntity.ok(response);
    }
}
