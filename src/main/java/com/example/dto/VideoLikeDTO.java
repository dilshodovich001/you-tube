package com.example.dto;

import com.example.entity.ProfileEntity;
import com.example.entity.VideoEntity;
import com.example.enums.Like;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoLikeDTO {
    private Integer id;
    private Integer profileId;
    private ProfileEntity profile;
    private String videoId;
    private VideoEntity video;
    private LocalDateTime createdDate;
    private Like type;

}
