package com.nopay.nopayapi.dto.products;

import java.math.BigDecimal;
import java.util.Set;

import com.nopay.nopayapi.dto.SellerDTO;

import lombok.Data;

@Data
public class ProductResponseDTO {

    private Integer idProduct;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Set<String> categories; // Changed to List<String>
    private Set<SizeDTO> sizes;
    private Set<String> colors;
    private SellerDTO seller;

}
