package com.nopay.nopayapi.entity.orders;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderItemId implements Serializable {

    private Integer orderId;
    private Integer productId;
    private Integer sizeId; // Add sizeId field

    public OrderItemId() {
    }

    public OrderItemId(Integer orderId, Integer productId, Integer sizeId) { // Include sizeId in constructor
        this.orderId = orderId;
        this.productId = productId;
        this.sizeId = sizeId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getSizeId() {
        return sizeId;
    }

    public void setSizeId(Integer sizeId) {
        this.sizeId = sizeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrderItemId that = (OrderItemId) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(productId, that.productId)
                && Objects.equals(sizeId, that.sizeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId, sizeId);
    }
}
