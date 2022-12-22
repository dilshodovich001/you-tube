package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    //    1. Create Category (ADMIN)
   // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/create")
    public HttpEntity<?> create(@RequestBody CategoryDTO categoryDto) {
        return ResponseEntity.ok(categoryService.create(categoryDto));
    }


    //    2. Update Category (ADMIN)
   // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/update/{id}")
    public HttpEntity<?> update(@PathVariable Integer id, @RequestBody CategoryDTO categoryDto) {
        return ResponseEntity.ok(categoryService.update(id, categoryDto));
    }

    //    3. Delete Category (ADMIN)
 //   @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/delete/{id}")
    public HttpEntity<?> delete(Integer id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }

    //    4. Category List
    @GetMapping("/category_list")
    public HttpEntity<?> categoryList() {
        return ResponseEntity.ok(categoryService.list());
    }

}
