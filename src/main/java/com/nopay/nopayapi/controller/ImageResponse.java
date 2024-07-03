package com.nopay.nopayapi.controller;

import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageResponse {
    private Set<String> file;
}
