package com.nopay.nopayapi.repository.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nopay.nopayapi.entity.products.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    ///
}
