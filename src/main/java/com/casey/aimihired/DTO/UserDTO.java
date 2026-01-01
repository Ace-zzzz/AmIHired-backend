package com.casey.aimihired.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    @JsonProperty("user_name")
    @NotBlank(message = "Username is required!")
    @Size(min = 8, max = 25, message = "Username should be between 8 and 25 characters")
    private String userName;

    @NotBlank(message = "Password is required!")
    @Size(min = 8, message = "Password should be at least 8 characters")
    private String password;

    @Email
    @NotBlank(message = "Email is required!")
    private String email;
}
