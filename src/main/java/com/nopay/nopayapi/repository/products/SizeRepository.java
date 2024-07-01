package com.nopay.nopayapi.repository.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nopay.nopayapi.entity.products.Size;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {
}