package com.nopay.nopayapi.service.products;

import com.nopay.nopayapi.dto.SellerDTO;
import com.nopay.nopayapi.dto.products.ProductRequestDTO;
import com.nopay.nopayapi.dto.products.ProductResponseDTO;
import com.nopay.nopayapi.dto.products.ProductUpdateRequestDTO;
import com.nopay.nopayapi.dto.products.SizeDTO;
import com.nopay.nopayapi.entity.products.*;
import com.nopay.nopayapi.entity.users.Role;
import com.nopay.nopayapi.entity.users.User;
import com.nopay.nopayapi.repository.products.*;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ColorRepository colorRepository;

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
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new UsernameNotFoundException("User not found");
        }
        User seller = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (seller.getRole().equals(Role.USER)) {
            throw new IllegalArgumentException("User is not a seller");
        }

        Product product = new Product();
        product.setSeller(seller);
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        Product productWithId = productRepository.save(product);

        if (productRequestDTO.getMaterial() != null && productRequestDTO.getMaterial().isPresent()) {
            materialRepository.findByDescription(productRequestDTO.getMaterial().get().getDescription())
                    .ifPresentOrElse(productWithId::setMaterial, () -> {
                        throw new IllegalArgumentException(
                                "Invalid material: " + productRequestDTO.getMaterial().get().getDescription());
                    });
        }

        final Product finalProductWithId = productWithId;
        Set<Size> sizes = productRequestDTO.getSizes().stream().map(sizeDTO -> {
            Size size = new Size();
            size.setDescription(sizeDTO.getSize());
            size.setStock(sizeDTO.getStock());
            size.setProduct(finalProductWithId); // Set the product
            return sizeRepository.save(size);
        }).collect(Collectors.toSet());

        productWithId.setSizes(sizes);

        // Initialize sizes collection
        Hibernate.initialize(productWithId.getSizes());

        Set<Category> categories = productRequestDTO.getCategories().stream().map(cat -> {
            Category category = categoryService.findByName(cat);
            if (category != null) {
                return category;
            }
            return null; // or handle null case if necessary
        }).filter(Objects::nonNull).collect(Collectors.toSet());

        productWithId.setCategories(categories);

        // Initialize categories collection
        Hibernate.initialize(productWithId.getCategories());

        Set<ProductColor> productColors = productRequestDTO.getColors().stream().map(colorDTO -> {
            Color color = colorRepository.findByName(colorDTO.getColorDescription())
                    .orElseGet(() -> {
                        Color newColor = new Color();
                        newColor.setName(colorDTO.getColorDescription());
                        return colorRepository.save(newColor);
                    });
            return new ProductColor(new ProductColorId(finalProductWithId.getIdProduct(), color.getIdColor()),
                    finalProductWithId,
                    color, colorDTO.getColorType());
        }).collect(Collectors.toSet());

        productWithId.setProductColors(productColors);

        // Initialize productColors collection
        Hibernate.initialize(productWithId.getProductColors());

        // Save the product again with all relationships set
        productWithId = productRepository.save(productWithId);

        return convertToDTO(productWithId);
    }

    @Transactional
    public ProductResponseDTO update(Product existingProduct, ProductUpdateRequestDTO productUpdateRequestDTO) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new UsernameNotFoundException("User not found");
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.getId().equals(existingProduct.getSeller().getId())) {
            throw new IllegalArgumentException("User is not the seller of the product");
        }

        existingProduct.setDescription(productUpdateRequestDTO.getDescription());
        existingProduct.setPrice(productUpdateRequestDTO.getPrice());
        existingProduct.setStock(productUpdateRequestDTO.getSizes().stream().mapToInt(SizeDTO::getStock).sum());

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
        existingProduct.getCategories().addAll(categories);

        existingProduct.getSizes().clear();
        Set<Size> sizes = new HashSet<>();
        productUpdateRequestDTO.getSizes().forEach(sizeDTO -> {
            Size size = new Size();
            size.setDescription(sizeDTO.getSize());
            size.setStock(sizeDTO.getStock());
            sizes.add(size);
        });

        existingProduct.setSizes(sizes);

        existingProduct.getProductColors().clear();
        Set<ProductColor> productColors = productUpdateRequestDTO.getColors().stream().map(colorDTO -> {
            Color color = colorRepository.findByName(colorDTO.getColorDescription())
                    .orElseThrow(
                            () -> new IllegalArgumentException("Invalid color: " + colorDTO.getColorDescription()));
            return new ProductColor(new ProductColorId(existingProduct.getIdProduct(), color.getIdColor()),
                    existingProduct, color, colorDTO.getColorType());
        }).collect(Collectors.toSet());

        existingProduct.setProductColors(productColors);

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
