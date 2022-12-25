package com.example.entity;

import com.example.enums.VideoStatus;
import com.example.enums.VideoType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "video")
public class VideoEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;
    private String title;

    private LocalDate createdDate;
    private LocalDate publishedDate;
    private VideoStatus videoStatus;
    private VideoType videoType;
    private Integer viewCount;


    private String description;
    private Long likeCount;
    private Long dislikeCount;

    @Column(name = "preview_attach_id")
    private String preview_attach_id;
    @JoinColumn(name = "preview_attach_id",insertable = false,updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private AttachEntity preview;

    @Column
    private Integer category_id;
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private CategoryEntity category;

    @Column
    private String attach_id;
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private AttachEntity attach;

    @Column
    private Long shared_count;


    // id(uuid), preview_attach_id,title,category_id,attach_id,created_date,published_date,
    // status(private,public),
    // type(video,short),view_count,shared_count,description,channel_id,(like_count,dislike_count),
    // category_id
    // view_count -> Okala view_count buyerda ham bo'lsin. Alohida Table ham bo'lsin.
    //       category_id -> bitta channel bitta category bo'lsin.
}
