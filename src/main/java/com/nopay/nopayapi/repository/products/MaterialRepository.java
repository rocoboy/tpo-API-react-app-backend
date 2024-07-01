package com.nopay.nopayapi.repository.products;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nopay.nopayapi.entity.products.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {

    Optional<Material> findByDescription(String description);
}
