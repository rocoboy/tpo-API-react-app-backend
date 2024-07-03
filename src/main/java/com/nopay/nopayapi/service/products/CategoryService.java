package com.nopay.nopayapi.service.products;

import com.nopay.nopayapi.dto.PaginatedResponse;
import com.nopay.nopayapi.dto.products.CategoryResponseDTO;
import com.nopay.nopayapi.entity.products.Category;
import com.nopay.nopayapi.entity.users.Role;
import com.nopay.nopayapi.entity.users.User;
import com.nopay.nopayapi.repository.products.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    @Transactional
    public Category findByName(String name) {
        Category cat = categoryRepository.findByName(name);
        if (cat == null) {
            return null;
        }
        return cat;
    }

    @Transactional
    public Category save(Category category) {

        // only admin role can create a category
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new UsernameNotFoundException("User not found");
        }
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!admin.getRole().equals(Role.ADMIN)) {
            throw new UsernameNotFoundException("Only Admin can create a category");
        }
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteById(Integer id) {

        // only admin role can delete a category
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new UsernameNotFoundException("User not found");
        }
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!admin.getRole().equals(Role.ADMIN)) {
            throw new UsernameNotFoundException("Only Admin can create a category");
        }

        categoryRepository.deleteById(id);
    }

    @Transactional
    public PaginatedResponse<Category> findAllPaginated(Integer page, Integer size) {
        List<Category> categories = categoryRepository.findAll();

        List<Category> paginatedCategories = categories.stream()
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());

        return new PaginatedResponse<>(paginatedCategories, page, size, categories.size());
    }

    public CategoryResponseDTO convertToDTO(Category category) {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setName(category.getName());
        return categoryResponseDTO;
    }
}
