package com.nopay.nopayapi.controller.products;

import com.nopay.nopayapi.entity.products.Category;
import com.nopay.nopayapi.service.products.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer id) {
        Optional<Category> category = categoryService.findById(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        try {
            Category savedCategory = categoryService.save(category);
            return ResponseEntity.ok(savedCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer id,
            @RequestBody Category categoryDescription) {
        Optional<Category> categoryOptional = categoryService.findById(id);

        if (categoryOptional.isPresent()) {
            Category existingCategory = categoryOptional.get();
            existingCategory.setDescription(categoryDescription.getDescription());
            existingCategory.setIdParent(categoryDescription.getIdParent());

            try {
                Category updatedCategory = categoryService.save(existingCategory);
                return ResponseEntity.ok(updatedCategory);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(null);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        Optional<Category> categoryOptional = categoryService.findById(id);

        if (categoryOptional.isPresent()) {
            categoryService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}