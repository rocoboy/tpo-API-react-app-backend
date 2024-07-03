package com.nopay.nopayapi.dto.products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageUploadRequestDTO {
    private String name;
    private String file; // Base64 encoded image
}
