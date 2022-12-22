package com.example.controller;

import com.example.dto.ProfileDTO;
import com.example.dto.auth.ChangeDTO;
import com.example.service.ProfileService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/admin/create")
    public ResponseEntity<ProfileDTO> save(@Valid @RequestBody ProfileDTO profileDTO) {
        log.info("Create Profile -> "+profileDTO);
        ProfileDTO profile = profileService.create(profileDTO);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/change")
    public ResponseEntity<ChangeDTO> change(@Valid @RequestBody ChangeDTO dto){
        log.info("Change Password -> " + dto);
        ChangeDTO response = profileService.change(dto);
        return ResponseEntity.ok(response);

    }
}
