package com.casey.aimihired.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.casey.aimihired.DTO.Job_application.GetJobDTO;
import com.casey.aimihired.DTO.Job_application.JobDTO;
import com.casey.aimihired.models.User;
import com.casey.aimihired.models.Job_application.Fulltime;
import com.casey.aimihired.models.Job_application.Internship;
import com.casey.aimihired.models.Job_application.Job;
import com.casey.aimihired.models.Job_application.PartTime;
import com.casey.aimihired.repo.JobRepo;
import com.casey.aimihired.repo.UserRepo;
import com.casey.aimihired.service.JobService;
import com.casey.aimihired.util.ApiResponse;

@Service
public class JobImpl implements JobService{
    private final JobRepo jobRepo;
    private final UserRepo userRepo;

    // DEPENDENCY INJECTION
    public JobImpl(JobRepo jobRepo, UserRepo userRepo) {
        this.jobRepo = jobRepo;
        this.userRepo = userRepo;
    }

    // CREATES JOB ENTRY
    @Override
    @Transactional
    public ApiResponse create(JobDTO dto, String username) {
        // FIND USER BY USERNAME
        User user = userRepo.findByUsername(username).orElseThrow(
            () -> new IllegalArgumentException("User with username not found")
        );

        // CREATE JOB TYPE
        Job job = processJobType(dto);

        /**
         * STATE TRANSFER 
         * TO CREATE JOB FOR 
         * COMMON FIELDS
         **/ 
        mapJobDTOToEntity(job, dto);

        job.setUser(user);

        // SAVE THE JOB ON DATABASE
        jobRepo.save(job);

        return new ApiResponse("Successfully created", true);
    }

    // GET ALL THE JOB
    @Override
    @Transactional(readOnly = true)
    public List<GetJobDTO> getAll() {
        return jobRepo.findAll()
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
        Job job = jobRepo.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Job with id " + id + " is not found")
        );

        return convertToDTO(job);
    }

    // UPDATE JOB
    @Override
    @Transactional
    public ApiResponse update(Long id, JobDTO dto) {
        /**
         * THROWS EXCEPTION
         * IF JOB NOT FOUND BY ID
         **/
        Job job = jobRepo.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Job with id " + id + " is not found")
        );

        /**
         * STATE TRANSFER 
         * TO UPDATE COMMON FIELDS
         **/ 
        mapJobDTOToEntity(job, dto);

        /**
         * UPDATE FIELDS FOR
         * SPECIFIC JOB TYPE
         **/
        updateJobType(job, dto);

        return new ApiResponse("Successfully updated", true);
    }

    // DELETES JOB
    @Override
    @Transactional
    public ApiResponse delete(Long id) {
        Job job = jobRepo.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Job with id " + id + " is not found")
        );

        jobRepo.delete(job);

        return new ApiResponse("Successfully deleted", true);
    }

    /**
     * CONVERTS ALL
     * THE JOB INTO DTO 
     **/
    private GetJobDTO convertToDTO(Job job) {
        GetJobDTO.GetJobDTOBuilder dtoBuilder = GetJobDTO.builder()
            .id(job.getId())
            .position(job.getPosition())
            .company(job.getCompany())
            .workModel(job.getWorkModel())
            .status(job.getStatus())
            .jobURL(job.getJobURL());

        if (job instanceof Fulltime fulltime) {
            dtoBuilder.jobType("FULL TIME")
                      .benefits(fulltime.getBenefits());
        }

        if (job instanceof PartTime partTime) {
            dtoBuilder.jobType("PART TIME")
                      .shiftSchedule(partTime.getShiftSchedule());
        }

        if (job instanceof Internship internship) {
            dtoBuilder.jobType("INTERNSHIP")
                      .hourRequired(internship.getHourRequired())
                      .isPaid(internship.getIsPaid());
        }

        return dtoBuilder.build();
    }

    // MAP DTO INTO JOB ENTITY
    private void mapJobDTOToEntity(Job job, JobDTO dto) {
        job.setPosition(dto.getPosition());
        job.setCompany(dto.getCompany());
        job.setWorkModel(dto.getWorkModel());
        job.setStatus(dto.getStatus());
        job.setJobURL(dto.getJobURL());
    }

    /**
     * SET FIELD FOR
     * SPECIFIC JOB TYPE 
     **/
    private Job processJobType(JobDTO dto) {
        return switch (dto.getJobType().toUpperCase()) {
            case "INTERNSHIP" -> {
                Internship internship = new Internship();
                internship.setHourRequired(dto.getHourRequired());
                internship.setIsPaid(dto.getIsPaid());

                yield internship;
            }

            case "FULL TIME" -> {
                Fulltime fulltime = new Fulltime();
                fulltime.setBenefits(dto.getBenefits());
                
                yield fulltime;
            }

            case "PART TIME" -> {
                PartTime partTime = new PartTime();
                partTime.setShiftSchedule(dto.getShiftSchedule());

                yield partTime;
            }
        
            default -> throw new IllegalArgumentException(
                "Unknow Job type " + dto.getJobType()
            );
        };
    }
    
    /**
     * UPDATE FIELDS FOR
     * SPECIFIC JOB TYPE
     **/
    private void updateJobType(Job job, JobDTO dto) {
        if (job instanceof Fulltime fulltime) 
            fulltime.setBenefits(dto.getBenefits());

        if (job instanceof PartTime partTime) 
                partTime.setShiftSchedule(dto.getShiftSchedule());
        
        if (job instanceof Internship internship) {
                internship.setIsPaid(dto.getIsPaid());
                internship.setHourRequired(dto.getHourRequired());
        }
    }
}
