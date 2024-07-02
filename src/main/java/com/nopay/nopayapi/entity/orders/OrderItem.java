package com.nopay.nopayapi.entity.orders;

import com.nopay.nopayapi.entity.products.Product;
import com.nopay.nopayapi.entity.products.Size;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItem {

    @EmbeddedId
    private OrderItemId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("sizeId") // Map sizeId
    @JoinColumn(name = "size_id", nullable = false)
    private Size size;

    private Integer quantity;
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrderItem that = (OrderItem) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
