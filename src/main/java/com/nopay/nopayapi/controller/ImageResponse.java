package com.nopay.nopayapi.controller;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageResponse {
    private Integer id;
    private String file;
}
