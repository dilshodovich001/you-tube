package com.example.service;

import com.example.dto.ChannelDTO;
import com.example.entity.AttachEntity;
import com.example.entity.ChannelEntity;
import com.example.enums.ChannelStatus;
import com.example.exceptions.ChannelNullExceptions;
import com.example.repository.AttachRepository;
import com.example.repository.ChannelRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Log4j2
@Service
public class ChannelService {

    private static final String s = "ChannelPhotos/";
    @Autowired
    AttachRepository attachRepository;
    @Autowired
    private ChannelRepository channelRepository;

    public ChannelDTO save(ChannelDTO channelDTO) {

        check(channelDTO);
        ChannelEntity channelEntity = new ChannelEntity();
        channelEntity.setName(channelDTO.getName());
        channelEntity.setDescription(channelDTO.getDescription());
        channelEntity.setStatus(ChannelStatus.ACTIVE);
        channelEntity.setProfile_id(null);
        channelEntity.setProfile(null);
        channelEntity.setPhoto_id(null);
        channelEntity.setPhoto(null);
        channelEntity.setBanner(null);
        channelEntity.setBanner_id(null);
        ChannelEntity save = channelRepository.save(channelEntity);
        return todto(save);
    }

    public ChannelDTO update(String id, ChannelDTO channelDTO) {
        if (id.isBlank())
            throw new ChannelNullExceptions("id is not provided");
        check(channelDTO);
        Optional<ChannelEntity> optionalChannel = channelRepository.findById(id);
        if (!optionalChannel.isPresent())
            throw new ChannelNullExceptions("channel is not found id=" + id);
        ChannelEntity update = channelRepository.update(channelDTO.getName(), channelDTO.getDescription(), id);
        return todto(update);
    }

    public ChannelDTO todto(ChannelEntity entity) {
        ChannelDTO dto = new ChannelDTO();
        dto.setId(entity.toString());
        dto.setStatus(entity.getStatus());
        dto.setProfile_id(entity.getProfile_id());
        dto.setName(entity.getName());
        dto.setBanner_id(entity.getBanner_id());
        dto.setBanner(entity.getBanner());
        dto.setProfile(entity.getProfile());
        dto.setDescription(entity.getDescription());
        dto.setPhoto_id(entity.getPhoto_id());
        dto.setPhoto(entity.getPhoto());
        return dto;
    }

    public ChannelEntity toEntity(ChannelDTO dto) {
        ChannelEntity entity = new ChannelEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setStatus(dto.getStatus());
        entity.setProfile(dto.getProfile());
        entity.setBanner(dto.getBanner());
        entity.setBanner_id(dto.getBanner_id());
        entity.setPhoto(dto.getPhoto());
        entity.setPhoto_id(dto.getPhoto_id());
        entity.setDescription(dto.getDescription());
        entity.setProfile_id(dto.getProfile_id());
        return entity;
    }

    public void check(ChannelDTO dto) {
        if (dto.getName().isBlank())
            throw new ChannelNullExceptions("name is null");
        if (dto.getDescription().isBlank())
            throw new ChannelNullExceptions("description is null");
    }

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/12/21
    }

    // mp3/jpg/npg/mp4
    public String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public Integer saveAttach(MultipartFile file, String id) {
        try {
            log.info("{Attach to channel save}" + file);
            // attaches/2022/04/23/UUID.png
            String attachPath = getYmDString(); // 2022/04/23
            String extension = getExtension(file.getOriginalFilename()); // .png
            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + "." + extension; // UUID.png

            File folder = new File(s + attachPath);  // attaches/2022/12/21/
            if (!folder.exists()) {
                folder.mkdirs();
            }

            byte[] bytes = file.getBytes();


            Path path = Paths.get(s + attachPath + "/" + fileName); // attaches/2022/04/23/UUID.png
            Files.write(path, bytes);
            AttachEntity attachEntity = new AttachEntity();
            attachEntity.setPath(attachPath);
            attachEntity.setExtension(extension);
            attachEntity.setSize(file.getSize());
            attachEntity.setOriginalName(file.getOriginalFilename());
            attachEntity.setCreatedData(LocalDateTime.now());
            attachEntity.setId(uuid);
            attachRepository.save(attachEntity);
            if (id.isBlank())
                throw new ChannelNullExceptions("id is not provided");
            Optional<ChannelEntity> optionalChannel = channelRepository.findById(id);
            if (!optionalChannel.isPresent())
                throw new ChannelNullExceptions("channel is not found id=" + id);
            Integer update = channelRepository.update(attachEntity.getId(), id);
            return update;
        } catch (IOException e) {
            log.warn("file not saved " + file.getOriginalFilename());
            e.printStackTrace();
        }
        return null;
    }

    public Integer saveBanner(MultipartFile file, String id) {
        try {
            log.info("{banner to channel save}" + file);
            // attaches/2022/04/23/UUID.png
            String attachPath = getYmDString(); // 2022/04/23
            String extension = getExtension(file.getOriginalFilename()); // .png
            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + "." + extension; // UUID.png

            File folder = new File(s + attachPath);  // attaches/2022/12/21/
            if (!folder.exists()) {
                folder.mkdirs();
            }

            byte[] bytes = file.getBytes();


            Path path = Paths.get(s + attachPath + "/" + fileName); // attaches/2022/04/23/UUID.png
            Files.write(path, bytes);
            AttachEntity attachEntity = new AttachEntity();
            attachEntity.setPath(attachPath);
            attachEntity.setExtension(extension);
            attachEntity.setSize(file.getSize());
            attachEntity.setOriginalName(file.getOriginalFilename());
            attachEntity.setCreatedData(LocalDateTime.now());
            attachEntity.setId(uuid);
            attachRepository.save(attachEntity);
            if (id.isBlank())
                throw new ChannelNullExceptions("id is not provided");
            Optional<ChannelEntity> optionalChannel = channelRepository.findById(id);
            if (!optionalChannel.isPresent())
                throw new ChannelNullExceptions("channel is not found id=" + id);
            Integer update = channelRepository.update_banner(attachEntity.getId(), id);
            return update;
        } catch (IOException e) {
            log.warn("file not saved " + file.getOriginalFilename());
            e.printStackTrace();
        }
        return null;
    }

    public Page<ChannelDTO> getAllByPagination(int page, int size) {

        Pageable paging = PageRequest.of(page, size);

        Page<ChannelEntity> pageObj = channelRepository.findAll(paging);

        List<ChannelEntity> list = pageObj.getContent();
        long totalElements = pageObj.getTotalElements();

        List<ChannelDTO> dtoList = new LinkedList<>();

        for (ChannelEntity entity : list) {
            ChannelDTO courseDto = new ChannelDTO();

            dtoList.add(courseDto);
        }

        Page<ChannelDTO> response = new PageImpl(dtoList, paging, totalElements);
        // return new EmployeePageResponseDTO(dtoList, totalElements);
        return response;
    }

    public ChannelDTO getById(String id) {
        if (id.isBlank())
            throw new ChannelNullExceptions("id is not provided");
        Optional<ChannelEntity> optionalChannel = channelRepository.findById(id);
        if (!optionalChannel.isPresent())
            throw new ChannelNullExceptions("channel is not found id=" + id);
        return todto(optionalChannel.get());

    }

    public String statusChange(String id, ChannelStatus status) {
        if (id.isBlank())
            throw new ChannelNullExceptions("id is not provided");
        Optional<ChannelEntity> optionalChannel = channelRepository.findById(id);
        if (!optionalChannel.isPresent())
            throw new ChannelNullExceptions("channel is not found id=" + id);

        Integer integer = channelRepository.updateStatus(id, status);
        if (integer != 0)
            return "unchanged";

        return "changed";

    }

    public List<ChannelDTO> getChannelsUser(String id) {
        if (id.isBlank())
            throw new ChannelNullExceptions("id is not provided");
        Optional<ChannelEntity> optionalChannel = channelRepository.findById(id);
        if (!optionalChannel.isPresent())
            throw new ChannelNullExceptions("channel is not found id=" + id);

        List<ChannelEntity> allByProfile_id = channelRepository.findAllByProfile_id(id);


        List<ChannelDTO> channelDTOS = new LinkedList<>();
        allByProfile_id.forEach(channelEntity -> {
            ChannelDTO dto = todto(channelEntity);
            channelDTOS.add(dto);
        });
        return channelDTOS;
    }

}
