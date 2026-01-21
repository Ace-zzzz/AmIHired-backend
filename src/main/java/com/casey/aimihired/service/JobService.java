package com.casey.aimihired.service;

import java.util.List;

import com.casey.aimihired.DTO.Job_application.GetJobDTO;
import com.casey.aimihired.DTO.Job_application.JobDTO;
import com.casey.aimihired.util.ApiResponse;

public interface JobService {
    public ApiResponse create(JobDTO dto);
    public List<GetJobDTO> getAll();
    public GetJobDTO get(Long id);
    public ApiResponse update(Long id, JobDTO dto);
    public ApiResponse delete(Long id);
}
