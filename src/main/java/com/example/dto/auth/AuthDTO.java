package com.example.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthDTO {
    @NotBlank
    @Size(min = 4, message = "Phone is required")
    private String phone;
    @NotBlank
    @Size(min = 4, message = "Password is required")
    private String password;
}