package com.nopay.nopayapi.entity.orders;

import com.nopay.nopayapi.entity.products.Product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_discount")
public class ProductDiscount {

    @EmbeddedId
    private ProductDiscountId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("discountCodeId")
    @JoinColumn(name = "discount_code_id")
    private DiscountCode discountCode;
}
