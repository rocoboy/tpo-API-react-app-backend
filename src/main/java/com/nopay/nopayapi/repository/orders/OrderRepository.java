package com.nopay.nopayapi.repository.orders;

import com.nopay.nopayapi.entity.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
