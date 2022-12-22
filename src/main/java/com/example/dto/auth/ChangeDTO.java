package com.example.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
public class ChangeDTO {
    @NotBlank
    @Size(min = 4, message = "Old Password is required")
    private String oldPassword;
    @NotBlank
    @Size(min = 4, message = "New Password is required")
    private String newPassword;

}