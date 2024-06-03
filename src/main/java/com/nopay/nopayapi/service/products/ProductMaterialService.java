package com.nopay.nopayapi.service.products;

import com.nopay.nopayapi.entity.products.ProductMaterial;
import com.nopay.nopayapi.repository.products.ProductMaterialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductMaterialService {

    @Autowired
    private ProductMaterialRepository productMaterialRepository;

    public List<ProductMaterial> findAll() {
        return productMaterialRepository.findAll();
    }

    public Optional<ProductMaterial> findById(long id) {
        return productMaterialRepository.findById(id);
    }

    public ProductMaterial save(ProductMaterial productMaterial) {
        return productMaterialRepository.save(productMaterial);
    }

    public void deleteById(long id) {
        productMaterialRepository.deleteById(id);
    }
}
