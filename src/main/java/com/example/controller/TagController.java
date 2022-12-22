package com.example.controller;

import com.example.dto.TagDTO;
import com.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    //1.Create Tag
    @PostMapping("/admin/create")
    public HttpEntity<?> create(@RequestBody TagDTO tagDTO) {
        return ResponseEntity.ok(tagService.create(tagDTO));
    }


    //    2.Update tag (ADMIN)
    // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/update/{id}")
    public HttpEntity<?> update(@PathVariable Integer id, @RequestBody TagDTO tagDTO) {
        return ResponseEntity.ok(tagService.update(id, tagDTO));
    }

    //    3.Delete tag (ADMIN)
    //   @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/delete/{id}")
    public HttpEntity<?> delete(Integer id) {
        return ResponseEntity.ok(tagService.delete(id));
    }

    //4.Tag List
    @GetMapping("/tag_list")
    public HttpEntity<?> tagList() {
        return ResponseEntity.ok(tagService.list());
    }


}
