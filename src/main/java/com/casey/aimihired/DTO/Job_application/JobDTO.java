package com.casey.aimihired.DTO.Job_application;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JobDTO {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(length = 50, nullable = false) 
    @NotBlank(message = "Position is required!")
    @Size(min = 2,  max = 50, message = "Position should be between 2 and 50 characters")
    private String position;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(length = 50, nullable = false) 
    @NotBlank(message = "Company is required!")
    @Size(min = 2,  max = 50, message = "Company should be between 2 and 50 characters")
    private String company;

    @JsonProperty(value = "work_model", access = JsonProperty.Access.WRITE_ONLY)
    @Column(length = 20, nullable = false) 
    @NotBlank(message = "Work model is required!")
    @Size(min = 2,  max = 20, message = "Work model should be between 2 and 20 characters")
    private String workModel;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(length = 20, nullable = false) 
    @NotBlank(message = "Status is required!")
    @Size(min = 2,  max = 20, message = "Status should be between 2 and 20 characters")
    private String status;

    @JsonProperty(value = "job_url", access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "job_URL")
    private String jobURL;
}
