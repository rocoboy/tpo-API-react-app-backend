package com.nopay.nopayapi.service.products;

import com.nopay.nopayapi.entity.products.ProductCategory;
import com.nopay.nopayapi.repository.products.ProductCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    public Optional<ProductCategory> findById(long id) {
        return productCategoryRepository.findById(id);
    }

    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    public void deleteById(long id) {
        productCategoryRepository.deleteById(id);
    }
}
