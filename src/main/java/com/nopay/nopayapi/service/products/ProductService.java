package com.nopay.nopayapi.service.products;

import com.nopay.nopayapi.entity.products.Product;
import com.nopay.nopayapi.repository.products.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findByColour(String colour) {
        return productRepository.findByColour(colour);
    }

    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> findByMaterial(String material) {
        return productRepository.findByMaterial(material);
    }

    public List<Product> findBySize(String size) {
        return productRepository.findBySize(size);
    }

    public List<Product> findByPrice(double price) {
        return productRepository.findByPrice(price);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
