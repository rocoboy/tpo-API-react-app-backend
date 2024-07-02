package com.nopay.nopayapi.dto.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Integer id;
    private String user;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private String status;
    private Set<OrderItemResponseDTO> items;
    private Set<OrderDiscountResponseDTO> discounts;
}
