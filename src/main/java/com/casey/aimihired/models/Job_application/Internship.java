package com.casey.aimihired.models.Job_application;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "internship")
public class Internship extends Job{
    @Column(length = 50, nullable = false) 
    @NotNull(message = "Hours Required is required!")
    private int hourRequired;

    @Column(nullable = false) 
    @NotNull(message = "Please specify whether the internship is paid.")
    private Boolean isPaid;
}
