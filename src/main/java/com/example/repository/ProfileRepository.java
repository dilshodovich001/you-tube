package com.example.repository;

import com.example.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {
    ProfileEntity findByPhone(String phone);

    ProfileEntity findByPhoneAndPassword(String phone, String encode);

    ProfileEntity findByEmail(String email);
}
