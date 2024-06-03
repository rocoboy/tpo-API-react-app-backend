package com.nopay.nopayapi.repository.products;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nopay.nopayapi.entity.products.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByColour(String colour);

    // This should be reworked

    List<Product> findByCategory(String category);

    List<Product> findByMaterial(String material);

    List<Product> findBySize(String size);

    List<Product> findByPrice(double price);

}
