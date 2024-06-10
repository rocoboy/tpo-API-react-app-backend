package com.nopay.nopayapi.repository.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nopay.nopayapi.entity.products.Colour;

@Repository
public interface ColourRepository extends JpaRepository<Colour, Integer> {
    ///
}