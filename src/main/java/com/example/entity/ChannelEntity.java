package com.example.entity;

import com.example.enums.ChannelStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Entity
@Table(name = "channel")
public class ChannelEntity {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;

    @Column
    private String name;
    @Column
    private String description;
    @Column()
    @Enumerated(EnumType.STRING)
    private ChannelStatus status;

    @Column(name = "banner_id")
    private String banner_id;
    @JoinColumn(name = "banner_id", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.EAGER)
    private AttachEntity banner;

    @Column(name = "profile_id")
    private Integer profile_id;
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ProfileEntity profile;

    @Column(name = "photo_id")
    private String photo_id;
    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private AttachEntity photo;


}
