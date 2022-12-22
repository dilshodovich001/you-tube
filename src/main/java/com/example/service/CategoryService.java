package com.example.service;

import com.example.dto.CategoryDTO;
import com.example.entity.CategoryEntity;
import com.example.exceptions.CategoryNullException;
import com.example.repository.CategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO categoryDto) {
        log.info("{category create} " + categoryDto);
        categoryDto.setCreated_date(LocalDate.now());
        check(categoryDto);
        CategoryEntity entity = toEntity(categoryDto);
        CategoryEntity save = categoryRepository.save(entity);
        categoryDto.setId(save.getId());
        return categoryDto;
    }

    public CategoryDTO update(Integer id, CategoryDTO categoryDto) {
        log.info("{Category update}" + categoryDto);
        check(categoryDto);
        CategoryEntity update = categoryRepository.update(id, categoryDto.getName());
        return toDto(update);
    }

    public String delete(Integer id) {
        if (id <= 0) {
            log.info("{delete category}" + id);
            throw new CategoryNullException("id must be bigger than 0" + id);
        }
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(id);
        if (!categoryEntityOptional.isPresent())
            throw new CategoryNullException("category with this id=" + id + " not found");
        categoryRepository.delete(categoryEntityOptional.get());
        return "deleted successfully";

    }

    public List<CategoryDTO> list() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new LinkedList<>();
        categoryEntityList.forEach(entity -> {
            CategoryDTO dto = toDto(entity);
            categoryDTOList.add(dto);
        });
        return null;
    }

    public CategoryEntity toEntity(CategoryDTO categoryDto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(categoryDto.getId());
        entity.setName(categoryDto.getName());
        entity.setCreated_date(categoryDto.getCreated_date());
        return entity;
    }


    public CategoryDTO toDto(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreated_date(entity.getCreated_date());
        return dto;
    }

    public void check(CategoryDTO dto) {
        if (dto.getName().isBlank()) {
            log.warn("{category check}" + dto);
            throw new CategoryNullException("Category_name is null");
        }
    }


}
