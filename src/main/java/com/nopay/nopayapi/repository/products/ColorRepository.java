package com.nopay.nopayapi.repository.products;

import com.nopay.nopayapi.entity.products.Color;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    Optional<Color> findByName(String name);
}