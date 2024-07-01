package com.nopay.nopayapi.controller.products;

import com.nopay.nopayapi.dto.products.ProductRequestDTO;
import com.nopay.nopayapi.dto.products.ProductResponseDTO;
import com.nopay.nopayapi.dto.products.ProductUpdateRequestDTO;
import com.nopay.nopayapi.entity.products.Product;
import com.nopay.nopayapi.service.products.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

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
}
