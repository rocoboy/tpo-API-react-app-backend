package com.nopay.nopayapi.controller.users;

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
    public Product createProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Optional<Product> product = productService.findById(id);

        // If the product does exist

        if (product.isPresent()) {
            Product updatedProduct = product.get();
            updatedProduct.setIdProduct(productDetails.getIdProduct());
        } else {
            return ResponseEntity.notFound().build();
        }
        return null;
    }
}
