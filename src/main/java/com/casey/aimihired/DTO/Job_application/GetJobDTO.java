package com.casey.aimihired.DTO.Job_application;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetJobDTO {
    // COMMON FIELDS
    private final Long id;
    @JsonProperty(value = "job_type")
    private final String jobType;
    private final String position;
    private final String company;
    @JsonProperty(value = "work_model")
    private final String workModel;
    private final String status;
    @JsonProperty(value = "job_url")
    private final String jobURL;

    // FULL TIME FIELD
    private final String benefits;

    // PART TIME FIELD
    @JsonProperty(value = "shift_schedule")
    private final String shiftSchedule;

    // INTERNSHIP FIELD
    @JsonProperty(value = "hours_required")
    private final Integer hourRequired;
    @JsonProperty(value = "is_paid")
    private final Boolean isPaid;
}
