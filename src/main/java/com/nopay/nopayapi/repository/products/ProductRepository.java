package com.nopay.nopayapi.repository.products;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nopay.nopayapi.entity.products.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByColour(Long id_colour);

    List<Product> findByCategory(Long id_category);

    List<Product> findByMaterial(Long id_material);

    List<Product> findBySize(Long id_size);

    List<Product> findByPrice(double price);

}
