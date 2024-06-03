package com.nopay.nopayapi.service.products;

import com.nopay.nopayapi.entity.products.ProductColour;
import com.nopay.nopayapi.repository.products.ProductColourRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductColourService {

    @Autowired
    private ProductColourRepository productCategoryRepository;

    public List<ProductColour> findAll() {
        return productCategoryRepository.findAll();
    }

    public Optional<ProductColour> findById(long id) {
        return productCategoryRepository.findById(id);
    }

    public ProductColour save(ProductColour productColour) {
        return productCategoryRepository.save(productColour);
    }

    public void deleteById(long id) {
        productCategoryRepository.deleteById(id);
    }
}
