package com.nopay.nopayapi.dto.products;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ProductRequestDTO {

    private String description;
    private BigDecimal price;
    private Set<SizeDTO> sizes;
    private List<String> categories;
    private Optional<MaterialDTO> material;
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

    public void setSizes(Set<SizeDTO> size) {
        this.sizes = size;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Optional<MaterialDTO> getMaterial() {
        return material;
    }

    public void setMaterial(Optional<MaterialDTO> material) {
        this.material = material;
    }

    public Set<ColorDTO> getColors() {
        return colors;
    }

    public void setColors(Set<ColorDTO> color) {
        this.colors = color;
    }

}
