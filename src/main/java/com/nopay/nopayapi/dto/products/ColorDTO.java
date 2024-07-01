package com.nopay.nopayapi.dto.products;

import com.nopay.nopayapi.entity.products.ProductColor;

import lombok.Data;

@Data
public class ColorDTO {

    private String colorDescription;
    private String colorHex;
    private ProductColor.ColorType colorType;

}
