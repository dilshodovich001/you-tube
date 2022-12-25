package com.example.controller;

import com.example.dto.ProfileDTO;
import com.example.dto.auth.ChangeDTO;
import com.example.dto.auth.ChangeEmailDTO;
import com.example.service.ProfileService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody ProfileDTO profileDTO) {
        int result = profileService.updateUser(profileDTO);

        if (result == 1) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


    @PutMapping("/update/email")
    public ResponseEntity<ChangeEmailDTO> changeEmail(@Valid @RequestBody ChangeEmailDTO dto){
        log.info("Change Password -> " + dto);
        ChangeEmailDTO response = profileService.updateEmail(dto);
        return ResponseEntity.ok(response);
    }


        @PreAuthorize("hasRole('USER')")
    @PutMapping("/update/attach")
    public ResponseEntity<?> attachUpdate(@RequestParam("id") String id){
        log.info("Change Password -> " + id);
        int response = profileService.attachUpdate(id);
        return ResponseEntity.ok(response);
    }


}
