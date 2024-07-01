
package com.nopay.nopayapi.dto.products;

import java.math.BigDecimal;
import java.util.List;

import com.nopay.nopayapi.entity.products.Category;

public class ProductUpdateRequestDTO {
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private List<String> categories; // Changed to List<String>

    // Getters and Setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
