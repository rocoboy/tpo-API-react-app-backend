package com.nopay.nopayapi.service.products;

import com.nopay.nopayapi.entity.products.ProductSeller;
import com.nopay.nopayapi.repository.products.ProductSellerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductSellerService {

    @Autowired
    private ProductSellerRepository productSellerRepository;

    public List<ProductSeller> findAll() {
        return productSellerRepository.findAll();
    }

    public Optional<ProductSeller> findById(long id) {
        return productSellerRepository.findById(id);
    }

    public ProductSeller save(ProductSeller productSeller) {
        return productSellerRepository.save(productSeller);
    }

    public void deleteById(long id) {
        productSellerRepository.deleteById(id);
    }
}
