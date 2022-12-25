package com.example.service;

import com.example.dto.ReportDto;
import com.example.dto.RepostInfoDto;
import com.example.entity.ProfileEntity;
import com.example.entity.ReportEntity;
import com.example.exceptions.ReportNullExceptios;
import com.example.repository.AttachRepository;
import com.example.repository.ProfileRepository;
import com.example.repository.ReportRepository;
import com.example.util.SpringSecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class ReportService {
    @Autowired
    ReportRepository repository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    AttachRepository attachRepository;

    public ReportDto create(ReportDto dto) {
        log.info("{create dto}" + dto);
        check(dto);
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setContent(dto.getContent());
        reportEntity.setEntityId(dto.getEntityId());
        reportEntity.setProfile_id(SpringSecurityUtil.getCurrentUserId());
        ReportEntity save = repository.save(reportEntity);
        dto.setId(save.getId());
        return dto;
    }

    public Page<RepostInfoDto> getAllByPagination(int page, int size) {

        log.info("{page$$size}" + page + "&&" + size);
        Pageable paging = PageRequest.of(page, size);

        Page<ReportEntity> pageObj = repository.findAll(paging);

        List<ReportEntity> list = pageObj.getContent();
        long totalElements = pageObj.getTotalElements();

        List<RepostInfoDto> dtoList = new LinkedList<>();

        for (ReportEntity entity : list) {
            RepostInfoDto repostInfoDto = new RepostInfoDto();
            repostInfoDto.setContent(entity.getContent());
            repostInfoDto.setType(entity.getType());
            repostInfoDto.setEntityId(entity.getEntityId());
            Optional<ProfileEntity> optionalProfile = profileRepository.findById(entity.getProfile_id());
            ProfileEntity profileEntity = optionalProfile.get();
            repostInfoDto.setProfileSurname(profileEntity.getSurname());
            repostInfoDto.setProfileId(entity.getProfile_id());
            switch (entity.getType()) {
                case VIDEO -> attachRepository.findById(entity.getEntityId());
            }


        }

        Page<RepostInfoDto> response = new PageImpl(dtoList, paging, totalElements);
        // return new EmployeePageResponseDTO(dtoList, totalElements);
        return response;
    }

    public List<ReportDto> findAllByProfile_id() {
        Integer currentUserId = SpringSecurityUtil.getCurrentUserId();
        List<ReportDto> reportDtoList = new LinkedList<>();
        List<ReportEntity> allByProfile_id = repository.allByProfileId(currentUserId);
        allByProfile_id.forEach(entity -> {

            ReportDto dto = toDto(entity);
            reportDtoList.add(dto);
        });
        return reportDtoList;
    }

    public String deleteById(Integer id) {
        log.info("{delete}" + id);
        if (id <= 0)
            throw new ReportNullExceptios("id must be higher than 0");
        Optional<ReportEntity> optionalReport = repository.findById(id);
        if (!optionalReport.isPresent())
            throw new ReportNullExceptios("report is not found id" + id);
        repository.deleteById(id);
        return "deleted sucessfully";
    }


    public ReportDto toDto(ReportEntity entity) {
        ReportDto dto = new ReportDto();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setContent(entity.getContent());
        dto.setProfile_id(entity.getProfile_id());
        dto.setEntityId(entity.getEntityId());
        dto.setEntity(entity.getEntity());
        return dto;
    }

    public ReportEntity toEntity(ReportDto dto) {
        ReportEntity entity = new ReportEntity();
        entity.setId(dto.getId());
        entity.setType(dto.getType());
        entity.setContent(dto.getContent());
        entity.setProfile_id(dto.getProfile_id());
        entity.setEntityId(dto.getEntityId());
        entity.setEntity(dto.getEntity());
        return entity;
    }

    public void check(ReportDto dto) {
        if (dto.getContent().isBlank())
            throw new ReportNullExceptios("Content is null");
    }


}

