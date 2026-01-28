package com.casey.aimihired.models.Job_application;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "internship")
@DiscriminatorValue("INTERNSHIP")
public class Internship extends Job{
    @JsonProperty(value = "hours_required", access = JsonProperty.Access.WRITE_ONLY)
    @Column(length = 50, nullable = false) 
    @NotNull(message = "Hours Required is required!")
    private Integer hourRequired;

    @JsonProperty(value = "is_paid", access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false) 
    @NotNull(message = "Please specify whether the internship is paid.")
    private Boolean isPaid;
}
