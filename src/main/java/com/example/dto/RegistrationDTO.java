package com.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RegistrationDTO {
    @NotBlank
    @Size(min = 5, message = "Name is required")
    private String name;
    @NotBlank
    @Size(min = 5, message = "Surname is required")
    private String surname;
    @NotBlank
    @Size(min = 4, message = "Phone is required")
    private String phone;
    @NotBlank

    @Size(min = 4, message = "Password is required")
    private String password;

    @Email
    private String email;
}
