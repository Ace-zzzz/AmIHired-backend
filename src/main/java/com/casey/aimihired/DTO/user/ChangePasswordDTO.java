package com.casey.aimihired.DTO.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ChangePasswordDTO {
    private String response;

    public ChangePasswordDTO(String response) {
        this.response = response;
    }

    @NotBlank(message = "Current Password is required")
    @JsonProperty(value = "current_password",  access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, message = "Current Password should be at least 8 characters")
    private String currentPassword;

    @NotBlank(message = "New Password is required")
    @JsonProperty(value = "new_password",  access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, message = "New Password should be at least 8 characters")
    private String newPassword;

    @NotBlank(message = "Confirm Password is required")
    @JsonProperty(value = "confirm_password",  access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, message = "Confirm Password should be at least 8 characters")
    private String confirmPassword;

}
