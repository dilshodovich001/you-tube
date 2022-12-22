package com.example.service;

import com.example.dto.TagDTO;
import com.example.entity.TagEntity;
import com.example.exceptions.CategoryNullException;
import com.example.repository.TagRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public TagDTO create(TagDTO tagDTO) {
        log.info("{Tag create} " + tagDTO);
        tagDTO.setCreated_date(LocalDate.now());
        check(tagDTO);
        TagEntity entity = toEntity(tagDTO);
        TagEntity save = tagRepository.save(entity);
        tagDTO.setId(save.getId());
        return tagDTO;
    }

    public TagDTO update(Integer id, TagDTO tagDTO) {
        log.info("{Tag update}" + tagDTO);
        check(tagDTO);
        TagEntity update = tagRepository.update(id, tagDTO.getName());
        return toDto(update);
    }

    public String delete(Integer id) {
        if (id <= 0) {
            log.info("{delete tag}" + id);
            throw new CategoryNullException("id must be bigger than 0" + id);
        }
        Optional<TagEntity> categoryEntityOptional = tagRepository.findById(id);
        if (!categoryEntityOptional.isPresent())
            throw new CategoryNullException("Tag with this id=" + id + " not found");
        tagRepository.delete(categoryEntityOptional.get());
        return "deleted successfully";

    }

    public List<TagDTO> list() {
        List<TagEntity> tagEntityList = tagRepository.findAll();
        List<TagDTO> tagDTOList = new LinkedList<>();
        tagEntityList.forEach(entity -> {
            TagDTO dto = toDto(entity);
            tagDTOList.add(dto);
        });
        return tagDTOList;
    }

    public TagEntity toEntity(TagDTO tagDTO) {
        TagEntity entity = new TagEntity();
        entity.setId(tagDTO.getId());
        entity.setName(tagDTO.getName());
        entity.setCreated_date(tagDTO.getCreated_date());
        return entity;
    }


    public TagDTO toDto(TagEntity entity) {
        TagDTO dto = new TagDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreated_date(entity.getCreated_date());
        return dto;
    }

    public void check(TagDTO dto) {
        if (dto.getName().isBlank()) {
            log.warn("{tag check}" + dto);
            throw new CategoryNullException("tag_name is null");
        }
    }

}
