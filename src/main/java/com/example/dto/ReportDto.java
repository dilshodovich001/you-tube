package com.example.dto;

import com.example.entity.ProfileEntity;
import com.example.enums.ReportType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {

    private Integer id;
    private String content;
    private String entityId;
    private ReportType type;

    private Integer profile_id;
    private ProfileEntity entity;

}
