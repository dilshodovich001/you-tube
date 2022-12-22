package com.example.dto.auth;

import com.example.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseDTO {
    private String name;
    private String surname;
    private String phone;
    private String password;
    private ProfileRole role;
    private String token;
}
