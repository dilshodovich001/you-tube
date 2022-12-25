package com.example.dto;

import com.example.entity.AttachEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.ChannelStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChannelDTO {


    private String id;
    private String name;
    private String description;
    private ChannelStatus status;

    private String banner_id;
    private AttachEntity banner;

    private Integer profile_id;
    private ProfileEntity profile;

    private String photo_id;
    private AttachEntity photo;
}
