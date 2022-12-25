package com.example.service;

import com.example.dto.ProfileDTO;
import com.example.dto.auth.ChangeDTO;
import com.example.dto.auth.ChangeEmailDTO;
import com.example.entity.AttachEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileStatus;
import com.example.exceptions.EmailAlreadyExistsException;
import com.example.exceptions.ItemNotFoundException;
import com.example.exceptions.WrongException;
import com.example.repository.AttachRepository;
import com.example.repository.ProfileRepository;
import com.example.util.Md5Util;
import com.example.util.SpringSecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;

@Service
@Slf4j
public class ProfileService {
    @Value("${attach.upload.folder}")
    private String attachFolder;


    @Value("${application.url}")
    private String attachOpenUrl;

    @Autowired
    private AttachService attachService;
    @Autowired private AttachRepository attachRepository;
    @Autowired EmailService emailService;

    @Autowired
    private ProfileRepository profileRepository;


    public ProfileEntity get(Integer id) {
        log.info("Profile get -> " + id);
        return profileRepository.findById(id).
                orElseThrow(() -> new ItemNotFoundException
                        ("Profile Not Found"));
    }

    public ProfileDTO create(ProfileDTO profileDTO) {
        ProfileEntity profile = profileRepository.findByPhone(profileDTO.getPhone());
        if (profile != null) {
            throw new EmailAlreadyExistsException("Phone already exists");
        }

        ProfileEntity entity = toEntity(profileDTO);
        entity.setPrtId(SpringSecurityUtil.getCurrentUserId());

        profileRepository.save(entity);

        profileDTO.setId(entity.getId());
        return profileDTO;

    }

    public ProfileEntity toEntity(ProfileDTO profileDTO) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(profileDTO.getName());
        entity.setSurname(profileDTO.getSurname());
        entity.setPhone(profileDTO.getPhone());
        entity.setPassword(Md5Util.encode(profileDTO.getPassword()));
        entity.setRole(profileDTO.getRole());
        entity.setVisible(true);
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setPhoto(attachService.get(profileDTO.getPhotoId()));
        return entity;
    }

    public ChangeDTO change(ChangeDTO dto) {
        ProfileEntity exists = profileRepository.findByPassword(Md5Util.encode(dto.getOldPassword()));
        if (exists == null){
            log.info("Not Found password -> "+ dto);
            throw new ItemNotFoundException("Not found");
        }
         int b = profileRepository.change(exists.getId(),Md5Util.encode(dto.getNewPassword()));
        if (b == 0){
            throw new WrongException("Error");
        }
        return dto;
    }

    public int updateUser(ProfileDTO profileDTO) {
        return profileRepository.updateUserById(profileDTO.getName(),
                profileDTO.getSurname(), SpringSecurityUtil.getCurrentUserId());
    }


    public ChangeEmailDTO updateEmail(ChangeEmailDTO dto) {
        ProfileEntity entity = profileRepository.findByEmail(dto.getOldEmail());
        if (entity == null){
            log.info("Not Found Email -> "+ dto);
            throw new ItemNotFoundException("Not found");
        }
        int b = profileRepository.changeEmail(dto.getNewEmail(),ProfileStatus.NOT_ACTIVE.name() , entity.getId());

        if (b == 0){
            throw new WrongException("Error");
        }
//        sendVerificationEmail(entity);
        return dto;
    }



    public int attachUpdate(String id) {
        Integer userId = SpringSecurityUtil.getCurrentUserId();
        ProfileEntity profile = get(userId);
        AttachEntity attach = attachRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Not found");
        });
         profileRepository.attachUpdate(id,profile.getId());
        File file = new File(attachFolder + attach.getPath() + "/" + id + "." + attach.getExtension());
        if (file.delete()) {
            attachRepository.deleteById(id);
        }
        return profileRepository.attachUpdate(id,userId);
    }
}
