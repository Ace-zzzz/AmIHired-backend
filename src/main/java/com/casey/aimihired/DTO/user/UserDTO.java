package com.casey.aimihired.DTO.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {
    @NotBlank(message = "Username is required!")
    @Size(min = 8, max = 25, message = "Username should be between 8 and 25 characters")
    private String username;

    @NotBlank(message = "Password is required!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, message = "Password should be at least 8 characters")
    private String password;

    @NotBlank(message = "Password Confirmation is required!")
    @JsonProperty(value = "confirm_password", access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, message = "Password Confirmation should be at least 8 characters")
    private String confirmPassword;

    @Email
    @NotBlank(message = "Email is required!")
    private String email;
}
