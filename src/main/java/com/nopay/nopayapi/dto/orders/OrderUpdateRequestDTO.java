package com.nopay.nopayapi.dto.orders;

import com.nopay.nopayapi.entity.orders.OrderDiscount;
import lombok.Data;

import java.util.Set;

@Data
public class OrderUpdateRequestDTO {
    private Set<OrderItemDTO> items;
    private Set<OrderDiscount> discounts;
}
