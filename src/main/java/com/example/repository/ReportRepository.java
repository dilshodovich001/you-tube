package com.example.repository;

import com.example.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {

    @Query("from ReportEntity R where R.profile_id=?1")
    List<ReportEntity> allByProfileId(Integer id);

}
