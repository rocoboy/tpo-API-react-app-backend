package com.nopay.nopayapi.service.products;

import com.nopay.nopayapi.entity.products.Product;
import com.nopay.nopayapi.repository.products.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findByColour(Integer colour) {
        return productRepository.findByColour(colour);
    }

    public List<Product> findByCategory(Integer category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> findByMaterial(Integer material) {
        return productRepository.findByMaterial(material);
    }

    public List<Product> findBySize(Integer size) {
        return productRepository.findBySize(size);
    }

    public List<Product> findByPrice(BigDecimal price) {
        return productRepository.findByPrice(price);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
