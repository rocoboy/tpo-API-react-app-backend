package com.nopay.nopayapi.service.products;

import com.nopay.nopayapi.dto.SellerDTO;
import com.nopay.nopayapi.dto.products.ColorDTO;
import com.nopay.nopayapi.dto.products.MaterialDTO;
import com.nopay.nopayapi.dto.products.ProductRequestDTO;
import com.nopay.nopayapi.dto.products.ProductResponseDTO;
import com.nopay.nopayapi.dto.products.ProductUpdateRequestDTO;
import com.nopay.nopayapi.dto.products.SizeDTO;
import com.nopay.nopayapi.entity.products.*;
import com.nopay.nopayapi.entity.users.Role;
import com.nopay.nopayapi.entity.users.User;
import com.nopay.nopayapi.repository.products.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
        User seller = getAuthenticatedUser();
        validateSeller(seller);

        Product product = createProductFromRequest(productRequestDTO, seller);
        product = productRepository.save(product);

        if (productRequestDTO.getMaterial() != null && productRequestDTO.getMaterial().isPresent()) {
            product.setMaterial(createAndSaveMaterial(productRequestDTO.getMaterial(), product));
        }

        product.setSizes(createAndSaveSizes(productRequestDTO.getSizes(), product));
        product.setCategories(createCategoriesFromRequest(productRequestDTO.getCategories()));
        product.setProductColors(createAndSaveProductColors(productRequestDTO.getColors(),
                product));

        product = productRepository.save(product);
        return convertToDTO(product);
    }

    private Material createAndSaveMaterial(Optional<MaterialDTO> material, Product product) {

        Material newMaterial = new Material();
        newMaterial.setDescription(material.get().getDescription());
        return materialRepository.save(newMaterial);
    }

    @Transactional
    public ProductResponseDTO update(Product existingProduct, ProductUpdateRequestDTO productUpdateRequestDTO) {
        User user = getAuthenticatedUser();
        validateProductOwnership(user, existingProduct);

        updateProductFromRequest(existingProduct, productUpdateRequestDTO);

        existingProduct.setSizes(createAndSaveSizes(productUpdateRequestDTO.getSizes(), existingProduct));
        existingProduct.setCategories(createCategoriesFromRequest(productUpdateRequestDTO.getCategories()));
        existingProduct
                .setProductColors(createAndSaveProductColors(productUpdateRequestDTO.getColors(), existingProduct));

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

    private User getAuthenticatedUser() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private void validateSeller(User seller) {
        if (seller.getRole().equals(Role.USER)) {
            throw new IllegalArgumentException("User is not a seller");
        }
    }

    private void validateProductOwnership(User user, Product product) {
        if (!user.getId().equals(product.getSeller().getId())) {
            throw new IllegalArgumentException("User is not the seller of the product");
        }
    }

    private Product createProductFromRequest(ProductRequestDTO productRequestDTO, User seller) {
        Product product = new Product();
        product.setSeller(seller);
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());

        return product;
    }

    private void updateProductFromRequest(Product product, ProductUpdateRequestDTO productUpdateRequestDTO) {
        product.setDescription(productUpdateRequestDTO.getDescription());
        product.setPrice(productUpdateRequestDTO.getPrice());
        product.setStock(productUpdateRequestDTO.getSizes().stream().mapToInt(SizeDTO::getStock).sum());
    }

    private Set<Size> createAndSaveSizes(Set<SizeDTO> sizeDTOs, Product product) {
        return sizeDTOs.stream().map(sizeDTO -> {
            Size size = new Size();
            size.setDescription(sizeDTO.getSize());
            size.setStock(sizeDTO.getStock());
            size.setProduct(product);
            return sizeRepository.save(size);
        }).collect(Collectors.toSet());
    }

    private Set<Category> createCategoriesFromRequest(List<String> categoryNames) {
        return categoryNames.stream().map(categoryService::findByName).filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private Set<ProductColor> createAndSaveProductColors(Set<ColorDTO> colorDTOs, Product product) {
        return colorDTOs.stream().map(colorDTO -> {
            Color color = colorRepository.findByName(colorDTO.getColorDescription())
                    .orElseGet(() -> {
                        Color newColor = new Color();
                        newColor.setName(colorDTO.getColorDescription());
                        return colorRepository.save(newColor);
                    });
            return new ProductColor(new ProductColorId(product.getIdProduct(), color.getIdColor()), product, color,
                    colorDTO.getColorType());
        }).collect(Collectors.toSet());
    }

    private ProductResponseDTO convertToDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setIdProduct(product.getIdProduct());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getStock());
        Set<String> categories = product.getCategories().stream().map(Category::getName).collect(Collectors.toSet());
        dto.setCategories(categories);
        dto.setSizes(product.getSizes().stream().map(size -> new SizeDTO(size.getDescription(), size.getStock()))
                .collect(Collectors.toSet()));
        Set<String> colors = product.getProductColors().stream().map(productColor -> productColor.getColor().getName())
                .collect(Collectors.toSet());
        dto.setColors(colors);
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
