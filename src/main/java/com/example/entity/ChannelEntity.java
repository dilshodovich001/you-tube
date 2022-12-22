package com.example.entity;

import com.example.enums.ChannelStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "channel")
public class ChannelEntity {

    @Id
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column
    private String name;
    @Column
    private String description;
    @Column()
    @Enumerated(EnumType.STRING)
    private ChannelStatus status;
    @Column
    private String banner;

    @Column(name = "profile_id")
    private Integer profile_id;
    @JoinColumn(name = "profile_id",insertable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ProfileEntity profile;

    @Column(name = "photo_id")
    private String photo_id;
    @JoinColumn(name = "photo_id",insertable = false,updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private AttachEntity photo;


}
