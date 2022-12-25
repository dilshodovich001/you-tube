package com.example.entity;

import com.example.enums.Like;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "video_like")
public class VideoLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "profile_id")
    private Integer profileId;
    @JoinColumn(name = "profile_id",insertable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ProfileEntity profile;

    @Column(name = "video_id")
    private String videoId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", updatable = false, insertable = false)
    private VideoEntity video;
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column
    @Enumerated(EnumType.STRING)
    private Like type;

}
