package com.casey.aimihired.DTO.Job_application;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JobDTO {
    /**
     * FIELD FOR 
     * JOB TABLE 
     **/
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Position is required!")
    @Size(min = 2,  max = 50, message = "Position should be between 2 and 50 characters")
    private String position;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Company is required!")
    @Size(min = 2,  max = 50, message = "Company should be between 2 and 50 characters")
    private String company;

    @JsonProperty(value = "work_model", access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Work model is required!")
    @Size(min = 2,  max = 20, message = "Work model should be between 2 and 20 characters")
    private String workModel;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Status is required!")
    @Size(min = 2,  max = 20, message = "Status should be between 2 and 20 characters")
    private String status;

    @JsonProperty(value = "job_url", access = JsonProperty.Access.WRITE_ONLY)
    private String jobURL;

    @JsonProperty(value = "job_type", access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Job type is required!")
    @Size(min = 2,  max = 20, message = "Job type should be between 2 and 20 characters")
    private String jobType;

    /**
     * FIELD FOR 
     * INTERNSHIP TABLE 
     **/
    @JsonProperty(value = "hours_required", access = JsonProperty.Access.WRITE_ONLY)
    private Integer hourRequired;

    @JsonProperty(value = "is_paid", access = JsonProperty.Access.WRITE_ONLY)
    private Boolean isPaid;

    /**
     * FIELD FOR 
     * PART TIME TABLE 
     **/
    @JsonProperty(value = "shift_schedule", access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 2,  max = 50, message = "Shift Schedule should be between 2 and 50 characters")
    private String shiftSchedule;

    /**
     * FIELD FOR 
     * FULL TIME TABLE 
     **/
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 2,  max = 50, message = "Benefits should be between 2 and 50 characters")
    private String benefits;

    /**
     * VALIDATE FIELD
     * FOR SPECIFIC JOB TYPE
     **/
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) 
    @AssertTrue(message = "Required fields for selected job type are missing.")
    public boolean isSelectedJobValid() {
        if (jobType == null) return true;

        return switch (jobType.toUpperCase()) {
            case "FULL TIME"  -> benefits      != null && !benefits.isBlank();
            case "PART TIME"  -> shiftSchedule != null && !shiftSchedule.isBlank();
            case "INTERNSHIP" -> hourRequired  != null && isPaid != null;
            default -> true;
        };
    }
}
