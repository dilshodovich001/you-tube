package com.example.service;

import com.example.entity.ProfileEntity;
import com.example.exceptions.ItemNotFoundException;
import com.example.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).
                orElseThrow(() -> new ItemNotFoundException
                        ("Profile Not Found"));
    }
}
