package com.nopay.nopayapi.entity.orders;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDiscountId implements Serializable {

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "discount_code_id")
    private Integer discountCodeId;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProductDiscountId that = (ProductDiscountId) o;
        return Objects.equals(productId, that.productId) && Objects.equals(discountCodeId, that.discountCodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, discountCodeId);
    }
}