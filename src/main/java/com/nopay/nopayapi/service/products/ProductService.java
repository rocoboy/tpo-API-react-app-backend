package com.nopay.nopayapi.service.products;

import com.nopay.nopayapi.dto.SellerDTO;
import com.nopay.nopayapi.dto.products.ProductRequestDTO;
import com.nopay.nopayapi.dto.products.ProductResponseDTO;
import com.nopay.nopayapi.dto.products.ProductUpdateRequestDTO;
import com.nopay.nopayapi.entity.products.Category;
import com.nopay.nopayapi.entity.products.Product;
import com.nopay.nopayapi.entity.users.Role;
import com.nopay.nopayapi.entity.users.User;
import com.nopay.nopayapi.repository.products.ProductRepository;
import com.nopay.nopayapi.service.products.CategoryService;
import com.nopay.nopayapi.repository.users.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Transactional
    public List<ProductResponseDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public Optional<ProductResponseDTO> findById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::convertToDTO);
    }

    @Transactional
    public Optional<Product> findEntityById(Integer id) {
        return productRepository.findById(id);
    }

    @Transactional
    public ProductResponseDTO save(ProductRequestDTO productRequestDTO) {
        // Check if the user has a valid token
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new UsernameNotFoundException("User not found");
        }
        User seller = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (seller.getRole().equals(Role.USER)) {
            throw new IllegalArgumentException("User is not a seller");
        }

        Product product = new Product();
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setStock(productRequestDTO.getQuantity());

        Set<Category> categories = new HashSet<>();
        productRequestDTO.getCategories().forEach(cat -> {
            Category category = categoryService.findByName(cat);
            if (category != null) {
                categories.add(category);
            }
        });

        product.setCategories(categories);
        product.setSeller(seller);

        Product savedProduct = productRepository.save(product);
        ProductResponseDTO response = convertToDTO(savedProduct);
        return response;
    }

    @Transactional
    public ProductResponseDTO update(Product existingProduct, ProductUpdateRequestDTO productUpdateRequestDTO) {
        // Check if the user has a valid token
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Check if the user is the seller of the product
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.getId().equals(existingProduct.getSeller().getId())) {
            throw new IllegalArgumentException("User is not the seller of the product");
        }

        existingProduct.setDescription(productUpdateRequestDTO.getDescription());
        existingProduct.setPrice(productUpdateRequestDTO.getPrice());
        existingProduct.setStock(productUpdateRequestDTO.getQuantity());

        Set<Category> categories = new HashSet<>();
        productUpdateRequestDTO.getCategories().forEach(cat -> {
            try {
                Category category = categoryService.findByName(cat);
                if (category != null) {
                    categories.add(category);
                }

            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid category: " + cat);
            }
        });

        existingProduct.getCategories().clear();

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDTO(updatedProduct);
    }

    @Transactional
    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }

    public List<ProductResponseDTO> findByCategories(List<String> categories) {
        List<Product> products = productRepository.findByCategories(categories);
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ProductResponseDTO convertToDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setIdProduct(product.getIdProduct());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getStock());
        Set<String> categories = product.getCategories().stream().map(Category::getName).collect(Collectors.toSet());
        dto.setCategories(categories);
        dto.setSeller(product.getSeller() != null ? convertToDTO(product.getSeller()) : null);
        return dto;
    }

    private SellerDTO convertToDTO(User seller) {
        SellerDTO dto = new SellerDTO();
        dto.setIdSeller(seller.getId());
        dto.setName(seller.getFirstName());
        return dto;
    }
}
