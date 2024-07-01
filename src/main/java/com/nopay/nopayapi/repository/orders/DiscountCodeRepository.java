package com.nopay.nopayapi.repository.orders;

import com.nopay.nopayapi.entity.orders.DiscountCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountCodeRepository extends JpaRepository<DiscountCode, Integer> {
    Optional<DiscountCode> findByCode(String code);
}