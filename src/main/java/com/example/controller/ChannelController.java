package com.example.controller;

import com.example.dto.ChannelDTO;
import com.example.enums.ChannelStatus;
import com.example.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/channel")
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    //1.Create Channel (USER)
    @PostMapping("/save")
    public HttpEntity<?> save(@RequestBody ChannelDTO channelDTO) {
        return ResponseEntity.ok(channelService.save(channelDTO));
    }

    // 2. Update Channel ( USER and OWNER)
    @PutMapping("/update/{id}")
    public HttpEntity<?> update(@PathVariable String id, @RequestBody ChannelDTO channelDTO) {
        return ResponseEntity.ok(channelService.update(id, channelDTO));
    }

    //3. Update Channel photo ( USER and OWNER)
    @PutMapping("/update_photo/{id}")
    public HttpEntity<?> update_photo(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(channelService.saveAttach(file, id));
    }

    // 4. Update Channel banner ( USER and OWNER)
    @PutMapping("/update_banner/{id}")
    public HttpEntity<?> update_banner(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(channelService.saveBanner(file, id));
    }

    //5. Channel Pagination (ADMIN)
    @GetMapping("/getList")
    public HttpEntity<?> getList(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return ResponseEntity.ok(channelService.getAllByPagination(page, size));
    }

    //6. Get Channel By Id
    public HttpEntity<?>getChannel(@PathVariable String id) {
        return ResponseEntity.ok(channelService.getById(id));
    }

    //7. Change Channel Status (ADMIN,USER and OWNER)
    @PutMapping("/update/status/{id}")
    public HttpEntity<?> updateStatus(@PathVariable String id, @RequestParam("status") ChannelStatus status) {
        return ResponseEntity.ok(channelService.statusChange(id, status));
    }

    //8. User Channel List (murojat qilgan userni)c
    @GetMapping("/getUserChannel/{id}")
    public HttpEntity<?> getUserChannel(@PathVariable String id) {
        return ResponseEntity.ok(channelService.getChannelsUser(id));
    }


    @PostMapping("/upload_photo/{id}")
    public HttpEntity<?> upload(@RequestBody MultipartFile file, @PathVariable String id) {
        return ResponseEntity.ok(channelService.saveAttach(file, id));
    }


}
