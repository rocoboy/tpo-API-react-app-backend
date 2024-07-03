package com.nopay.nopayapi.controller.products;

import com.nopay.nopayapi.controller.ImageResponse;
import com.nopay.nopayapi.dto.products.ImageUploadRequestDTO;
import com.nopay.nopayapi.dto.products.ProductRequestDTO;
import com.nopay.nopayapi.dto.products.ProductResponseDTO;
import com.nopay.nopayapi.dto.products.ProductUpdateRequestDTO;
import com.nopay.nopayapi.entity.Image;
import com.nopay.nopayapi.entity.products.Product;
import com.nopay.nopayapi.service.ImageService;
import com.nopay.nopayapi.service.products.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Integer id) {
        Optional<ProductResponseDTO> product = productService.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        try {
            ProductResponseDTO savedProduct = productService.save(productRequestDTO);
            return ResponseEntity.ok(savedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id,
            @RequestBody ProductUpdateRequestDTO productUpdateRequestDTO) {
        Optional<Product> productOptional = productService.findEntityById(id);

        if (productOptional.isPresent()) {
            try {
                ProductResponseDTO updatedProduct = productService.update(productOptional.get(),
                        productUpdateRequestDTO);
                return ResponseEntity.ok(updatedProduct);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        Optional<Product> productOptional = productService.findEntityById(id);

        if (productOptional.isPresent()) {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/categories")
    public List<ProductResponseDTO> getProductsByCategories(@RequestParam List<String> categories) {
        return productService.findByCategories(categories);
    }

    @GetMapping("/seller/{sellerId}")
    public List<ProductResponseDTO> getProductsBySeller(@PathVariable Integer sellerId) {
        return productService.findBySeller(sellerId);
    }

    public ResponseEntity<?> getProductImages(@PathVariable Integer id) {
        try {
            Set<Image> images = productService.getProductImages(id);
            return ResponseEntity.ok(images);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @CrossOrigin
    @GetMapping("/{id}/images")
    public ResponseEntity<?> displayImage(@PathVariable Integer id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product ID is required.");
        }

        Optional<Product> productOptional = productService.findEntityById(id);
        if (productOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        Set<Image> images = productService.getProductImages(id);
        if (images.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product has no images.");
        }

        Set<ImageResponse> imageResponses = images.stream().map(img -> {
            ImageResponse imageResponse = new ImageResponse();
            imageResponse.setName(img.getName());
            imageResponse.setFile(Base64.getEncoder().encodeToString(img.getImage()));
            return imageResponse;
        }).collect(Collectors.toSet());

        return ResponseEntity.ok(imageResponses);
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<String> addImages(@PathVariable Integer id,
            @RequestBody Set<ImageUploadRequestDTO> imageUploadRequests) {
        try {
            Optional<Product> productOptional = productService.findEntityById(id);
            if (productOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
            }

            Product product = productOptional.get();

            Set<Image> images = imageUploadRequests.stream().map(imageUploadRequest -> {
                // Decode the Base64 encoded image
                byte[] bytes = Base64.getDecoder().decode(imageUploadRequest.getFile());

                // Create a new instance of Image with the image bytes
                Image image = new Image();
                image.setImage(bytes);
                image.setProduct(product);

                return image;
            }).collect(Collectors.toSet());

            // Save the images in the database
            imageService.saveAll(images);

            return ResponseEntity.status(HttpStatus.CREATED).body("Images added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add images.");
        }
    }

}
