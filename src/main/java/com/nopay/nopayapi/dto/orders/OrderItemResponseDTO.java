package com.nopay.nopayapi.dto.orders;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDTO {
    private Integer productId;
    private String productDescription;
    private String sizeDescription;
    private Integer quantity;
    private BigDecimal price;
}
