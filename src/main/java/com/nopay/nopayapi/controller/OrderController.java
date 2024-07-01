package com.nopay.nopayapi.controller;

import com.nopay.nopayapi.dto.orders.OrderCreateRequestDTO;
import com.nopay.nopayapi.entity.orders.Order;
import com.nopay.nopayapi.service.orders.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderCreateRequestDTO request) {
        Order order = orderService.createOrder(request.getItems(), request.getDiscountCode());
        return ResponseEntity.ok(order);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Integer userId) {
        List<Order> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Integer orderId) {
        Order order = orderService.getOrder(orderId);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Integer orderId,
            @RequestBody OrderCreateRequestDTO request) {
        Order updatedOrder = orderService.updateOrder(orderId, request.getItems(), request.getDiscountCode());
        return ResponseEntity.ok(updatedOrder);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Integer orderId, @RequestParam String status) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }
}
