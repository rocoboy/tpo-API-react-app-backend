
package com.nopay.nopayapi.dto.products;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class ProductUpdateRequestDTO {
    private String description;
    private BigDecimal price;
    private List<String> categories; // Changed to List<String>
    private Set<SizeDTO> sizes;
    private Set<ColorDTO> colors;

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

    public Set<SizeDTO> getSizes() {
        return sizes;
    }

    public void setSizes(Set<SizeDTO> sizes) {
        this.sizes = sizes;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Set<ColorDTO> getColors() {
        return colors;
    }

    public void setColors(Set<ColorDTO> colors) {
        this.colors = colors;
    }
}
