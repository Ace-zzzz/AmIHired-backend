package com.casey.aimihired.service;

import java.util.List;

import com.casey.aimihired.DTO.Job_application.GetJobDTO;
import com.casey.aimihired.DTO.Job_application.JobDTO;
import com.casey.aimihired.util.ApiResponse;

public interface JobService {
    public ApiResponse create(JobDTO dto, String username);
    public List<GetJobDTO> getAll(String username);
    public GetJobDTO get(Long id, String username);
    public ApiResponse update(Long id, JobDTO dto, String username);
    public ApiResponse delete(Long id, String username);
}
