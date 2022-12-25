package com.example.repository;

import com.example.dto.ChannelDTO;
import com.example.entity.ChannelEntity;
import com.example.enums.ChannelStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<ChannelEntity, String> {

    @Modifying
    @Transactional
    @Query("update ChannelEntity C set C.name=?1 , C.description=?2 where C.id=?3")
    ChannelEntity update(String name, String description, String channel_id);

    @Modifying
    @Transactional
    @Query("update ChannelEntity C set C.photo_id=?1 where C.id=?2")
    Integer update(String photo_id, String channelId);

    @Modifying
    @Transactional
    @Query("update ChannelEntity C set C.banner_id=?1 where C.id=?2")
    Integer update_banner(String banner_id, String channelId);


    @Modifying
    @Transactional
    @Query("UPDATE ChannelEntity C set C.status=?2 where C.id=?1")
    Integer updateStatus(String id, ChannelStatus status);

    List<ChannelEntity> findAllByProfile_id(String id);
}
