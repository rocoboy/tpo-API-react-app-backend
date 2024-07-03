package com.nopay.nopayapi.controller;

import com.nopay.nopayapi.dto.orders.OrderCreateRequestDTO;
import com.nopay.nopayapi.dto.orders.OrderItemDTO;
import com.nopay.nopayapi.dto.orders.OrderResponseDTO;
import com.nopay.nopayapi.entity.orders.Order;
import com.nopay.nopayapi.service.orders.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Set<OrderItemDTO> items,
            @RequestParam(required = false) List<String> discountCodes) {
        try {
            OrderResponseDTO orderResponse = orderService.createOrder(items, discountCodes);
            return ResponseEntity.ok(orderResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Integer userId) {
        List<Order> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Integer orderId) {
        Order order = orderService.getOrder(orderId);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer orderId) {
        try {
            orderService.deleteOrder(orderId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Integer orderId,
            @RequestBody OrderCreateRequestDTO request, @RequestParam(required = false) List<String> discountCodes) {
        Order updatedOrder = orderService.updateOrder(orderId, request.getItems(), discountCodes);
        return ResponseEntity.ok(updatedOrder);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Integer orderId, @RequestParam String status) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }
}
