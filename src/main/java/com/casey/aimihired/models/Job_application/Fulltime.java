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
@Table(name = "fulltime")
public class Fulltime extends Job{
    @Column(length = 50, nullable = false) 
    @NotBlank(message = "Benefits is required!")
    @Size(min = 2,  max = 50, message = "Benefits should be between 2 and 50 characters")
    private String benefits;
}
