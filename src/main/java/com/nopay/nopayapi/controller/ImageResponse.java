package com.nopay.nopayapi.controller;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageResponse {
    private String name;
    private String file; // Base64 encoded image
}
