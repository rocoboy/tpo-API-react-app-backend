package com.nopay.nopayapi.dto.products;

import com.nopay.nopayapi.entity.products.AcceptedSizes;

public class SizeDTO {
    public AcceptedSizes size;
    public Integer stock;

    public SizeDTO(AcceptedSizes size, Integer stock) {
        this.size = size;
        this.stock = stock;
    }

    public AcceptedSizes getSize() {
        return size;
    }

    public Integer getStock() {
        return stock;
    }
}
