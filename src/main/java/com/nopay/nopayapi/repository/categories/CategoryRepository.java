package com.nopay.nopayapi.repository.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nopay.nopayapi.entity.categories.Category;;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}