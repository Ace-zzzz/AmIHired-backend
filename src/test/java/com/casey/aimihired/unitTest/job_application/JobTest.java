package com.casey.aimihired.unitTest.job_application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.casey.aimihired.DTO.Job_application.JobDTO;
import com.casey.aimihired.impl.JobImpl;
import com.casey.aimihired.models.Job_application.Job;
import com.casey.aimihired.repo.JobRepo;

@ExtendWith(MockitoExtension.class)
public class JobTest {
    @Mock
    private JobRepo repo;

    @InjectMocks
    private JobImpl jobService;

    @Test
    void createJob_shouldReturnSuccessMessage_whenNoException() {
        // ARRANGE
        JobDTO dto = new JobDTO();

        dto.setPosition("full-stack");
        dto.setCompany("Manu life");
        dto.setWorkModel("on-site");
        dto.setStatus("pending");
        dto.setJobURL("www.youtube.com");

        // ACT
        JobDTO response = jobService.create(dto);

        /**
         * GET THE ACTUAL 
         * JOB THAT SAVED
         **/ 
        ArgumentCaptor<Job> job = ArgumentCaptor.forClass(Job.class);

        // ASSERT
        assertEquals("Successfully created", response.getResponse());

        /**
         * VERIFY THE REPO IS
         * CALLED EXACTLY ONCE
         **/
        verify(repo, times(1)).save(job.capture());
        verifyNoMoreInteractions(repo);
    }
}
