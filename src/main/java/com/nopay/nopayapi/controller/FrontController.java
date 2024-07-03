package com.nopay.nopayapi.controller;

import com.nopay.nopayapi.dto.PaginatedResponse;
import com.nopay.nopayapi.dto.orders.OrderResponseDTO;
import com.nopay.nopayapi.dto.products.ProductResponseDTO;
import com.nopay.nopayapi.service.orders.OrderService;
import com.nopay.nopayapi.service.products.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/front")
public class FrontController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    private static final int MAX_PAGE_SIZE = 50; // Define maximum page size

    @GetMapping("/products")
    public ResponseEntity<PaginatedResponse<ProductResponseDTO>> getAllProductsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (size > MAX_PAGE_SIZE) {
            size = MAX_PAGE_SIZE;
        }
        PaginatedResponse<ProductResponseDTO> response = productService.findAllPaginated(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders")
    public ResponseEntity<PaginatedResponse<OrderResponseDTO>> getAllOrdersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (size > MAX_PAGE_SIZE) {
            size = MAX_PAGE_SIZE;
        }
        PaginatedResponse<OrderResponseDTO> response = orderService.findAllPaginated(page, size);
        return ResponseEntity.ok(response);
    }

    // Add more endpoints for other entities as needed
}
