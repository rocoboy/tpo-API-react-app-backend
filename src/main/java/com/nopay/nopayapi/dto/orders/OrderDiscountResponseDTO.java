package com.nopay.nopayapi.dto.orders;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDiscountResponseDTO {
    private String description;
    private BigDecimal discountAmount;
}