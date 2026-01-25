package com.casey.aimihired.models.Job_application;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.casey.aimihired.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "jobs")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "job_type")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
