package com.nopay.nopayapi.repository.orders;

import com.nopay.nopayapi.entity.orders.Order;
import com.nopay.nopayapi.entity.orders.OrderDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDiscountRepository extends JpaRepository<OrderDiscount, Integer> {

    void deleteAllByOrder(Order order);
}
