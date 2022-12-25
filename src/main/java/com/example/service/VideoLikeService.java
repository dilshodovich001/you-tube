package com.example.service;

import com.example.dto.VideoLikeDTO;
import com.example.entity.VideoLikeEntity;
import com.example.enums.Like;
import com.example.repository.VideoLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.instrument.ClassDefinition;

@Service
public class VideoLikeService {
    @Autowired
    private VideoLikeRepository videoLikeRepository;

    public void create(VideoLikeDTO dto) {
        VideoLikeEntity exists = get(dto);
        if (exists != null) {
            updateStatus(dto);
            return;
        }
        VideoLikeEntity entity = new VideoLikeEntity();
        entity.setVideoId(dto.getVideoId());
        entity.setProfileId(dto.getProfileId());
        entity.setType(dto.getType());
        videoLikeRepository.save(entity);
    }

    public void updateStatus(VideoLikeDTO dto) {
        VideoLikeEntity entity1 = get(dto);

        if (entity1.getType().equals(dto.getType())) {
            delete(dto);
            return;
        } else if (entity1.getType().equals(Like.LIKE)) {
            dto.setType(Like.DISLIKE);
        } else if (entity1.getType().equals(Like.DISLIKE)) {
            dto.setType(Like.LIKE);
        }
        entity1.setType(dto.getType());
        videoLikeRepository.save(entity1);

    }

    public VideoLikeEntity get(VideoLikeDTO dto) {
        return videoLikeRepository.findByProfileIdAndVideoId(dto.getProfileId(),
                dto.getVideoId());
    }

    public void delete(VideoLikeDTO dto) {
        videoLikeRepository.deleted(dto.getProfileId(), dto.getVideoId());
    }
}
