package com.example.repository;

import com.example.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<TagEntity, Integer> {

    @Query("UPDATE TagEntity T SET T.name=?2 where T.id=?1")
    TagEntity update(Integer id, String name);
}
