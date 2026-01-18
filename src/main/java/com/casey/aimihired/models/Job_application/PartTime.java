package com.casey.aimihired.models.Job_application;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "part_time")
public class PartTime extends Job{
    @Column(length = 50, nullable = false) 
    @NotBlank(message = "Shift Schedule is required!")
    @Size(min = 2,  max = 50, message = "Shift Schedule should be between 2 and 50 characters")
    private String shiftSchedule;
}
