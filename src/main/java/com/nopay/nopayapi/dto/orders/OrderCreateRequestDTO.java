package com.nopay.nopayapi.dto.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequestDTO {
    private Set<OrderItemDTO> items;
    private List<String> discountCode;
}
