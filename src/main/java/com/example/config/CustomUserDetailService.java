package com.example.config;

import com.example.entity.ProfileEntity;
import com.example.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        log.info("{authorization }"+phone);
        ProfileEntity optional = profileRepository.findByPhone(phone);
        if (optional == null) {
            throw new UsernameNotFoundException("Bad Cretetional");
        }

        return new CustomUserDetails(optional);
    }
}
