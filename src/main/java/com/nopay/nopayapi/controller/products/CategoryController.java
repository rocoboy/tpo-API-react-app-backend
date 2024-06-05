package com.nopay.nopayapi.controller.products;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nopay.nopayapi.entity.products.Category;
import com.nopay.nopayapi.service.products.CategoryService;

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
    public ResponseEntity<Category> getCategoryByID(@PathVariable Long id) {
        Optional<Category> category = categoryService.findById(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> createCategory(@RequestBody Category category) {
        if (category.getDetails() == null) {
            return ResponseEntity.badRequest().body("Details is a required field.");
        }

        List<Category> allCategories = categoryService.findAll();
        if (allCategories.isEmpty()) {
            Category savedCategory = categoryService.save(category);
            savedCategory.setIdParent(savedCategory.getIdCategory());
            Category updatedCategory = categoryService.save(savedCategory);
            return ResponseEntity.ok(updatedCategory);
        } else if (category.getIdParent() == null) {
            return ResponseEntity.badRequest().body("idParent is a required field.");
        } else {
            Optional<Category> parentCategory = categoryService.findById(category.getIdParent());
            if (!parentCategory.isPresent()) {
                return ResponseEntity.badRequest().body("idParent does not exist.");
            }
        }

        Category savedCategory = categoryService.save(category);
        return ResponseEntity.ok(savedCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            Category updatedCategory = category.get();

            updatedCategory.setDetails(categoryDetails.getDetails());
            if (categoryDetails.getIdParent() != null) {
                Optional<Category> parentCategory = categoryService.findById(categoryDetails.getIdParent());
                if (!parentCategory.isPresent()) {
                    return ResponseEntity.badRequest().body("idParent does not exist.");
                }
                updatedCategory.setIdParent(categoryDetails.getIdParent());
            }

            return ResponseEntity.ok(categoryService.save(updatedCategory));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        Optional<Category> categoryOptional = categoryService.findById(id);

        if (categoryOptional.isPresent()) {
            categoryService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
