package com.casey.aimihired.DTO.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateUserNameDTO {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "user_name")
    @NotBlank(message = "Username is required!")
    @Size(min = 8, max = 25, message = "Username should be between 8 and 25 characters")
    private String userName;

}
