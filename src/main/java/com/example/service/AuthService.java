package com.example.service;

import com.example.dto.auth.AuthDTO;
import com.example.dto.auth.AuthResponseDTO;
import com.example.dto.RegistrationDTO;
import com.example.entity.EmailEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.LangEnum;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exceptions.AppForbiddenException;
import com.example.exceptions.EmailAlreadyExistsException;
import com.example.exceptions.PasswordOrEmailWrongException;
import com.example.repository.EmailRepository;
import com.example.repository.ProfileRepository;
import com.example.util.JwtTokenUtil;
import com.example.util.Md5Util;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AuthService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired ProfileService profileService;
    @Autowired
    private ResourceBundleService resourceService;
    @Autowired
    private EmailRepository emailRepository;

    public AuthResponseDTO login(AuthDTO dto, LangEnum lang) {

        ProfileEntity profile = profileRepository.
                findByPhoneAndPassword(dto.getPhone(),
                        Md5Util.encode(dto.getPassword()));

        if (profile == null) {
            throw new PasswordOrEmailWrongException(resourceService.getMessage("credential.wrong",lang.name()));
        }
        if (!profile.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new AppForbiddenException("No Access");
        }
        return toDTO(profile);
    }

    private AuthResponseDTO toDTO(ProfileEntity profile) {
        AuthResponseDTO dto = new AuthResponseDTO();
        dto.setName(profile.getName());
        dto.setSurname(profile.getSurname());
        dto.setRole(profile.getRole());
        dto.setToken(JwtTokenUtil.encode(profile.getPhone(), profile.getRole()));
        dto.setName(profile.getName());
        dto.setSurname(profile.getSurname());
        dto.setRole(profile.getRole());
        return dto;
    }

    public String registration(RegistrationDTO dto) {
        ProfileEntity exists = profileRepository.findByEmail(dto.getEmail());
        if (exists != null) {
            if (exists.getStatus().equals(ProfileStatus.NOT_ACTIVE)) {
                profileRepository.delete(exists);
            } else {
                throw new EmailAlreadyExistsException("Email already exists");
            }
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setPassword(Md5Util.encode(dto.getPassword()));
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.NOT_ACTIVE);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(true);
        entity.setEmail(dto.getEmail());
        profileRepository.save(entity);
        Thread thread = new Thread() {
            @Override
            public void run() {
                sendVerificationEmail(entity);
            }
        };
        thread.start();

        return "Confirm that the message has been sent to your email";
    }

    private void sendVerificationEmail(ProfileEntity entity) {
        StringBuilder builder = new StringBuilder();
        String encode = JwtTokenUtil.encode(entity.getPhone(), entity.getRole());
        builder.append("<h1 style=\"text-align: center\">Complete Registration</h1>");
        String link = String.format("<a href=\"http://localhost:8081/auth/verification/email/%s\"> Click there</a>", encode);
        builder.append(link);
        String title = "Activate Your Registration";
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setEmail(entity.getEmail());
        emailEntity.setMessage(encode);
        emailEntity.setCreatedDate(LocalDateTime.now());
        emailRepository.save(emailEntity);
        emailService.sendEmailMime(entity.getEmail(), title, builder.toString());
    }
    public String verification(String jwt) {
        Integer id;
        try {
            id = JwtTokenUtil.decodeForEmailVerification(jwt);
        } catch (JwtException e) {
            return "Verification failed";
        }

        ProfileEntity exists = profileService.get(id);
        if (!exists.getStatus().equals(ProfileStatus.NOT_ACTIVE)) {
            return "Verification failed";
        }
        exists.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(exists);

        return "Verification success";
    }

}
