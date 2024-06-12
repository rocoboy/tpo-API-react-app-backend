package com.nopay.nopayapi.repository.products;

import com.nopay.nopayapi.entity.products.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.categories")
    List<Product> findAllWithCategories();
}
