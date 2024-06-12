package com.nopay.nopayapi.dto.products;

import java.math.BigDecimal;
import java.util.Set;

import com.nopay.nopayapi.dto.SellerDTO;
import com.nopay.nopayapi.dto.CategoryDTO;

public class ProductResponseDTO {

    private Integer idProduct;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Set<CategoryDTO> categories;
    private SellerDTO seller;

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

    public Set<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryDTO> categories) {
        this.categories = categories;
    }

    public SellerDTO getSeller() {
        return seller;
    }

    public void setSeller(SellerDTO seller) {
        this.seller = seller;
    }

}
