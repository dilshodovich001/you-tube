package com.example.dto;

import com.example.dto.auth.ChangeDTO;
import com.example.entity.AttachEntity;
import com.example.entity.CategoryEntity;
import com.example.entity.ChannelEntity;
import com.example.enums.VideoStatus;
import com.example.enums.VideoType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoDTO {
    private String id;

    private String attachId;
    private String title;
    private Integer categoryId;
    private String attachVideoId;
    private LocalDateTime publishedDate;
    private VideoType type;
    private Long viewCount;
    private Long sharedCount;
    private String description;
    private String channelId;

    private Long likeCount;

    private Long dislikeCount;
    private CategoryDTO category;
    private ChangeDTO channel;
    private VideoStatus status;
    private LocalDateTime createdDate;
    private AttachDTO attachDTO;
    private AttachDTO attachVideo;
}
