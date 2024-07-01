package com.nopay.nopayapi.dto.orders;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Integer productId;
    private Integer quantity;
}
