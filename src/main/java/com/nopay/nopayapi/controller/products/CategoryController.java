package com.nopay.nopayapi.controller.products;

import com.nopay.nopayapi.dto.CategoryRequestDTO;
import com.nopay.nopayapi.entity.products.Category;
import com.nopay.nopayapi.service.products.CategoryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequestDTO categoryRequest) {
        Category category = new Category(categoryRequest.getName());
        return ResponseEntity.ok(categoryService.save(category));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCategory(@RequestBody CategoryRequestDTO categoryRequest) {
        Category category = categoryService.findByName(categoryRequest.getName());
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        categoryService.deleteById(category.getId());
        return ResponseEntity.noContent().build();
    }

}