package com.example.repository;

import com.example.entity.VideoLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface VideoLikeRepository extends JpaRepository<VideoLikeEntity,Integer>
{

VideoLikeEntity findByProfileIdAndVideoId(Integer profileId,String videoId);


    @Transactional
    @Modifying
    @Query("delete from VideoLikeEntity a where a.profileId = ?1 and a.videoId = ?2")
    void deleted(Integer profileId, String videoId);

}
