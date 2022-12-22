package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class EmailDTO {
    private Integer id;
    @NotBlank @Size(message = "To Email is required")
    private String toEmail;
    @NotBlank @Size(message = "Title is required")
    private String title;
    @NotBlank @Size(message = "Content is required")
    private String content;
    private LocalDateTime createdDate;
}
