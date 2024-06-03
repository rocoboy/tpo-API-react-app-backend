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

    public List<Product> findByColour(Long id_colour) {
        return productRepository.findByColour(id_colour);
    }

    public List<Product> findByCategory(Long id_category) {
        return productRepository.findByCategory(id_category);
    }

    public List<Product> findByMaterial(Long id_material) {
        return productRepository.findByMaterial(id_material);
    }

    public List<Product> findBySize(Long id_size) {
        return productRepository.findBySize(id_size);
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
