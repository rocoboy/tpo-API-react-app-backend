package com.nopay.nopayapi.dto.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCodeDTO {
    private Integer id;
    private String code;
    private BigDecimal discountAmount;
    private Boolean isWholeOrder;
    private String description;
    private Boolean active;
    private Integer sellerId;
}
