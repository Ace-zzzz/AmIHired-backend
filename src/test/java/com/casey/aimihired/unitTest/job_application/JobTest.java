package com.casey.aimihired.unitTest.job_application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.casey.aimihired.DTO.Job_application.GetJobDTO;
import com.casey.aimihired.DTO.Job_application.JobDTO;
import com.casey.aimihired.impl.JobImpl;
import com.casey.aimihired.models.Job_application.Job;
import com.casey.aimihired.repo.JobRepo;
import com.casey.aimihired.util.ApiResponse;

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
        ApiResponse response = jobService.create(dto);

        /**
         * GET THE ACTUAL 
         * JOB THAT SAVED
         **/ 
        ArgumentCaptor<Job> job = ArgumentCaptor.forClass(Job.class);

        /**
         * VERIFY THE REPO IS
         * CALLED EXACTLY ONCE
         **/
        verify(repo, times(1)).save(job.capture());
        verifyNoMoreInteractions(repo);

        // ASSERT
        assertEquals("Successfully created", response.message());
    }

    @Test
    void getAllJob_shouldReturnListOfJobs_ifTheresAny() {
        // ACT
        jobService.getAll();

        /**
         * VERIFY THE REPO IS
         * CALLED EXACTLY ONCE
         **/
        verify(repo, times(1)).findAll();
        verifyNoMoreInteractions(repo);
    }

    @Test
    void getSingleJob_shouldReturnJob_ifJobExist() {
        // ARRANGE
        Long id = 1L;
        
        Job entity = new Job();
        entity.setId(id);
        entity.setPosition("testPosition");
        entity.setCompany("testCompany");
        entity.setWorkModel("testWorkModel");
        entity.setStatus("testStatys");
        entity.setJobURL("testJobURL");

        /**
         * MOCK REPOSITORY CALL 
         * TO SIMULATE THE FINDING OF JOB BY ID
         **/
        when(repo.findById(id)).thenReturn(Optional.of(entity));
        
        // ACT
        GetJobDTO result = jobService.get(entity.getId());

        /**
         * VERIFY THE REPO IS
         * CALLED EXACTLY ONCE
         **/
        verify(repo, times(1)).findById(entity.getId());
        verifyNoMoreInteractions(repo);
        
        // ASSERT
        assertEquals(entity.getPosition(), result.getPosition());
        assertEquals(entity.getCompany(), result.getCompany());
        assertEquals(entity.getWorkModel(), result.getWorkModel());
        assertEquals(entity.getStatus(), result.getStatus());
        assertEquals(entity.getJobURL(), result.getJobURL());
    }

    @Test
    void getSingleJob_shouldThrowException_ifJobDidNotExist() {
        // ARRANGE
        Long wrongId = 404L;
        
        // ACT
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> jobService.get(wrongId)
        );

        /**
         * VERIFY THE REPO IS
         * CALLED EXACTLY ONCE
         **/
        verify(repo, times(1)).findById(wrongId);
        verifyNoMoreInteractions(repo);

        // ASSERT
        assertEquals(exception.getMessage(), "Job with id " + wrongId + " is not found");
    }

    @Test
    void updateJob_shouldThrowException_ifJobDidNotExist() {
        // ARRANGE
        Long wrongId = 404L;
        
        JobDTO dto = new JobDTO();
        dto.setCompany("test Company");
        dto.setPosition("test Position");
        dto.setStatus("test Status");
        dto.setWorkModel("test Work model");
        dto.setJobURL("test Job URL");

        // ACT
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> jobService.update(wrongId, dto)
        );

        /**
         * VERIFY THE REPO IS
         * CALLED EXACTLY ONCE
         **/
        verify(repo, times(1)).findById(wrongId);
        verifyNoMoreInteractions(repo);

        // ASSERT
        assertEquals(exception.getMessage(), "Job with id " + wrongId + " is not found");
    }

    @Test
    void updateJob_shouldThrowSuccessMessage_ifJobExistAndNoProblem() {
        // ARRANGE
        Long id = 1L;
        
        JobDTO dto = new JobDTO();
        dto.setCompany("test Company");
        dto.setPosition("test Position");
        dto.setStatus("test Status");
        dto.setWorkModel("test Work model");
        dto.setJobURL("test Job URL");

        Job job = new Job();
        job.setId(id);

        /**
         * MOCK REPOSITORY CALL 
         * TO SIMULATE THE FINDING OF JOB BY ID
         **/
        when(repo.findById(id)).thenReturn(Optional.of(job));

        // ACT
        ApiResponse response = jobService.update(id, dto);

        /**
         * VERIFY THE REPO IS
         * CALLED EXACTLY ONCE
         **/
        verify(repo, times(1)).findById(id);
        verifyNoMoreInteractions(repo);

        // ASSERT 
        assertEquals("Successfully updated", response.message());
        assertEquals(true, response.success());
        
        assertEquals(dto.getCompany(), job.getCompany());
        assertEquals(dto.getPosition(), job.getPosition());
        assertEquals(dto.getStatus(), job.getStatus());
        assertEquals(dto.getWorkModel(), job.getWorkModel());
        assertEquals(dto.getJobURL(), job.getJobURL());
    }

    @Test
    void deleteJob_shouldThrowException_ifJobDidNotExist() {
        // ARRANGE
        Long wrongId = 404L;
        
        JobDTO dto = new JobDTO();
        dto.setCompany("test Company");
        dto.setPosition("test Position");
        dto.setStatus("test Status");
        dto.setWorkModel("test Work model");
        dto.setJobURL("test Job URL");

        // ACT
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> jobService.delete(wrongId)
        );

        /**
         * VERIFY THE REPO IS
         * CALLED EXACTLY ONCE
         **/
        verify(repo, times(1)).findById(wrongId);
        verifyNoMoreInteractions(repo);

        // ASSERT
        assertEquals(exception.getMessage(), "Job with id " + wrongId + " is not found");
    }

    @Test
    void deleteJob_shouldThrowSuccessMessage_ifJobExistAndNoProblem() {
        // ARRANGE
        Long id = 1L;
        
        Job job = new Job();
        job.setId(id);

        /**
         * MOCK REPOSITORY CALL 
         * TO SIMULATE THE FINDING OF JOB BY ID
         **/
        when(repo.findById(id)).thenReturn(Optional.of(job));

        // ACT
        ApiResponse response = jobService.delete(id);

        /**
         * VERIFY THE REPO IS
         * CALLED EXACTLY ONCE
         **/
        verify(repo, times(1)).findById(id);
        verify(repo, times(1)).delete(job);
        verifyNoMoreInteractions(repo);

        // ASSERT 
        assertEquals("Successfully deleted", response.message());
        assertEquals(true, response.success());
    }
}
