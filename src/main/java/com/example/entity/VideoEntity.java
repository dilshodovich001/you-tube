package com.example.entity;

import com.example.enums.VideoStatus;
import com.example.enums.VideoType;
import com.sun.jdi.event.StepEvent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.bytecode.internal.bytebuddy.BytecodeProviderImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "video")
public class VideoEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "preview_attach_id")
    private String attachId;
    @JoinColumn(name = "preview_attach_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AttachEntity previewAttach;
    @Column
    private String title;
    @Column(name = "category_id")
    private Integer categoryId;
    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity category;
    @Column(name = "attach_id")
    private String attachVideoId;
    @JoinColumn(name = "attach_id")
    @OneToOne(fetch = FetchType.LAZY)
    private AttachEntity attachVideo;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "published_date")
    private LocalDateTime publishedDate;
    @Column
    @Enumerated(EnumType.STRING)
    private VideoStatus status;
    @Column
    @Enumerated(EnumType.STRING)
    private VideoType type;
    @Column(name = "view_count")
    private Long viewCount;
    @Column(name = "shared_count")
    private Long sharedCount;
    @Column
    private String description;
    @Column(name = "channel_id")
    private String channelId;
    @JoinColumn(name = "channel_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ChannelEntity channel;

    @Column(name = "like_count")
    private Long likeCount;

    @Column(name = "dislike_count")
    private Long dislikeCount;
}