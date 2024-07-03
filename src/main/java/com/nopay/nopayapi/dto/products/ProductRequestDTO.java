package com.nopay.nopayapi.dto.products;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import lombok.Data;

@Data
public class ProductRequestDTO {

    private String description;
    private BigDecimal price;
    private Set<SizeDTO> sizes;
    private List<String> categories;
    private Optional<MaterialDTO> material;
    private Set<ColorDTO> colors;
    private Set<ImageUploadRequestDTO> images;

}
