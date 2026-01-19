package com.casey.aimihired.service;

import java.util.List;

import com.casey.aimihired.DTO.Job_application.GetJobDTO;
import com.casey.aimihired.DTO.Job_application.JobDTO;

public interface JobService {
    public JobDTO create(JobDTO dto);
    public List<GetJobDTO> getAll();
    public GetJobDTO get(Long id);
}
