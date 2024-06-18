package com.nopay.nopayapi.service.products;

import com.nopay.nopayapi.dto.CategoryDTO;
import com.nopay.nopayapi.dto.SellerDTO;
import com.nopay.nopayapi.dto.products.ProductRequestDTO;
import com.nopay.nopayapi.dto.products.ProductResponseDTO;
import com.nopay.nopayapi.dto.products.ProductUpdateRequestDTO;
import com.nopay.nopayapi.entity.products.Category;
import com.nopay.nopayapi.entity.products.Product;
import com.nopay.nopayapi.entity.users.Seller;
import com.nopay.nopayapi.repository.products.CategoryRepository;
import com.nopay.nopayapi.repository.products.ProductRepository;
import com.nopay.nopayapi.repository.users.SellerRepository;

import org.springframework.beans.factory.annotation.Autowired;
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
    private CategoryRepository categoryRepository;

    @Autowired
    private SellerRepository sellerRepository;

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
        Set<Category> categories = new HashSet<>();
        if (productRequestDTO.getCategoryIds() != null && !productRequestDTO.getCategoryIds().isEmpty()) {
            for (Integer categoryId : productRequestDTO.getCategoryIds()) {
                categoryRepository.findById(categoryId).ifPresent(categories::add);
            }
        }

        Seller seller = sellerRepository.findById(productRequestDTO.getSellerId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Seller ID"));

        Product product = new Product();
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setQuantity(productRequestDTO.getQuantity());
        product.setCategories(categories);
        product.setSeller(seller);

        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    @Transactional
    public ProductResponseDTO update(Product existingProduct, ProductUpdateRequestDTO productUpdateRequestDTO) {
        Set<Category> categories = new HashSet<>();
        if (productUpdateRequestDTO.getCategoryIds() != null && !productUpdateRequestDTO.getCategoryIds().isEmpty()) {
            for (Integer categoryId : productUpdateRequestDTO.getCategoryIds()) {
                categoryRepository.findById(categoryId).ifPresent(categories::add);
            }
        }

        existingProduct.setDescription(productUpdateRequestDTO.getDescription());
        existingProduct.setPrice(productUpdateRequestDTO.getPrice());
        existingProduct.setQuantity(productUpdateRequestDTO.getQuantity());
        existingProduct.setCategories(categories);

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDTO(updatedProduct);
    }

    @Transactional
    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }

    private ProductResponseDTO convertToDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setIdProduct(product.getIdProduct());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setCategories(product.getCategories().stream().map(this::convertToDTO).collect(Collectors.toSet()));
        dto.setSeller(product.getSeller() != null ? convertToDTO(product.getSeller()) : null);
        return dto;
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setIdCategory(category.getIdCategory());
        dto.setDescription(category.getDescription());
        return dto;
    }

    private SellerDTO convertToDTO(Seller seller) {
        SellerDTO dto = new SellerDTO();
        dto.setIdSeller(seller.getIdSeller());
        dto.setName(seller.getName());
        return dto;
    }
}
