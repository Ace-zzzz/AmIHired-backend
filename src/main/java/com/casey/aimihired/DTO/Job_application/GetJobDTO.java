package com.casey.aimihired.DTO.Job_application;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetJobDTO {
    private final Long id;
    private final String position;
    private final String company;
    private final String workModel;
    private final String status;
    private final String jobURL;
}
