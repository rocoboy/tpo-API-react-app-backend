package com.nopay.nopayapi.repository.orders;

import com.nopay.nopayapi.entity.orders.ProductDiscount;
import com.nopay.nopayapi.entity.orders.ProductDiscountId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDiscountRepository extends JpaRepository<ProductDiscount, ProductDiscountId> {
}