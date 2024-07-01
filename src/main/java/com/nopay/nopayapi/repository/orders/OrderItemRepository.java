package com.nopay.nopayapi.repository.orders;

import com.nopay.nopayapi.entity.orders.Order;
import com.nopay.nopayapi.entity.orders.OrderItem;
import com.nopay.nopayapi.entity.orders.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
    void deleteAllByOrder(Order order);
}
