package com.example.entity;

import com.example.enums.ReportType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "report")
public class ReportEntity {
    //Entity--->report
    // id,profile_id,content,entity_id(channel_id,profile_id),type(channel,video)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String content;

    @Column
    private String entityId;

    @Enumerated(EnumType.STRING)
    private ReportType type;

    @Column(name = "profile_id")
    private Integer profile_id;
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.EAGER)
    private ProfileEntity entity;

}
