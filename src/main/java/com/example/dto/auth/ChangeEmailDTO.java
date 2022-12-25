package com.example.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChangeEmailDTO {
    @NotBlank
    @Size(min = 4, message = "Old Email is required")
    private String oldEmail;
    @NotBlank
    @Size(min = 4, message = "New Email is required")
    private String newEmail;

}