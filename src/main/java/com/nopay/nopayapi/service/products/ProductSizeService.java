package com.nopay.nopayapi.service.products;

import com.nopay.nopayapi.entity.products.ProductSize;
import com.nopay.nopayapi.repository.products.ProductSizeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductSizeService {

    @Autowired
    private ProductSizeRepository productSizeRepository;

    public List<ProductSize> findAll() {
        return productSizeRepository.findAll();
    }

    public Optional<ProductSize> findById(long id) {
        return productSizeRepository.findById(id);
    }

    public ProductSize save(ProductSize productSeller) {
        return productSizeRepository.save(productSeller);
    }

    public void deleteById(long id) {
        productSizeRepository.deleteById(id);
    }
}
