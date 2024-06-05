package com.nopay.nopayapi.controller.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nopay.nopayapi.entity.products.Product;
import com.nopay.nopayapi.service.products.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product savedProduct = productService.save(product);
            return ResponseEntity.ok(savedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Optional<Product> productOptional = productService.findById(id);

        if (productOptional.isPresent()) {
            Product existingProduct = productOptional.get();

            if (productDetails.getDescription() != null) {

                existingProduct.setDescription(productDetails.getDescription());

            }

            if (productDetails.getPrice() != null) {

                existingProduct.setPrice(productDetails.getPrice());

            }

            if (productDetails.getQuantity() != null) {

                existingProduct.setQuantity(productDetails.getQuantity());

            }

            if (!productDetails.getCategories().isEmpty() || productDetails.getCategories() != null) {

                existingProduct.setCategories(productDetails.getCategories());
            }

            try {
                Product updatedProduct = productService.save(existingProduct);
                return ResponseEntity.ok(updatedProduct);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(null);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);

        if (productOptional.isPresent()) {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
