package com.example.controller;

import com.example.dto.VideoLikeDTO;
import com.example.service.VideoLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/video_like")
public class VideoLikeController {
    @Autowired
    private VideoLikeService videoLikeService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody VideoLikeDTO dto){
        videoLikeService.create(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody VideoLikeDTO dto){
        videoLikeService.updateStatus(dto);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody VideoLikeDTO dto){
        videoLikeService.delete(dto);
        return ResponseEntity.ok().build();
    }
}
