package com.casey.aimihired.DTO.Job_application;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetJobDTO {
    private Long id;
    private String position;
    private String company;
    private String workModel;
    private String status;
    private String jobURL;
}
