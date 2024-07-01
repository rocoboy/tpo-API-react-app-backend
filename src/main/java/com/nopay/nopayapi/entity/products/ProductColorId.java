package com.nopay.nopayapi.entity.products;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductColorId implements Serializable {

    private Integer productId;
    private Integer colorId;

    // Default constructor
    public ProductColorId() {
    }

    public ProductColorId(Integer productId, Integer colorId) {
        this.productId = productId;
        this.colorId = colorId;
    }

    // Getters, setters, equals, and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProductColorId that = (ProductColorId) o;
        return Objects.equals(productId, that.productId) && Objects.equals(colorId, that.colorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, colorId);
    }
}
