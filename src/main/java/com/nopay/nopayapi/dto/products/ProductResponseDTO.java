package com.nopay.nopayapi.dto.products;

import java.math.BigDecimal;
import java.util.Set;

import com.nopay.nopayapi.dto.SellerDTO;

public class ProductResponseDTO {

    private Integer idProduct;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Set<String> categories; // Changed to List<String>
    private SellerDTO seller;

    // Getters and Setters
    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

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

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public SellerDTO getSeller() {
        return seller;
    }

    public void setSeller(SellerDTO seller) {
        this.seller = seller;
    }
}
