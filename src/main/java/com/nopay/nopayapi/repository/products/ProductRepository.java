package com.nopay.nopayapi.repository.products;

import com.nopay.nopayapi.entity.products.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAll();

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c IN :categories")
    List<Product> findByCategories(List<String> categories);
}
