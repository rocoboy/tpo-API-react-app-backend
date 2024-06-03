package com.nopay.nopayapi.service.categories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nopay.nopayapi.entity.products.Category;
import com.nopay.nopayapi.repository.products.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRespRepository;

    public List<Category> findAll() {
        return categoryRespRepository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return categoryRespRepository.findById(id);
    }

    public Category save(Category category) {
        return categoryRespRepository.save(category);
    }

    public void deleteById(Long id) {
        categoryRespRepository.deleteById(id);
    }
}