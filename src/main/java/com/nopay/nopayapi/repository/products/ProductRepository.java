package com.nopay.nopayapi.repository.products;

import java.math.BigDecimal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nopay.nopayapi.entity.products.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByColour(Integer colour);

    List<Product> findByCategory(Integer category);

    List<Product> findByMaterial(Integer material);

    List<Product> findBySize(Integer size);

    List<Product> findByPrice(BigDecimal price);

}
