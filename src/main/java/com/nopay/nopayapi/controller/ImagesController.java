package com.nopay.nopayapi.controller;

import org.springframework.web.bind.annotation.RestController;
import com.nopay.nopayapi.entity.Image;
import com.nopay.nopayapi.service.ImageService;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("images")
public class ImagesController {

    @Autowired
    private ImageService imageService;

    @GetMapping
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> images = imageService.getAllImages();
        return ResponseEntity.ok().body(images);
    }

    @PostMapping()
    public ResponseEntity<String> addImage(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) {
        try {
            // Obtener los bytes de la imagen
            byte[] bytes = file.getBytes();

            // Crear una nueva instancia de Image con el nombre y los bytes de la imagen
            Image image = new Image();
            image.setImageBytes(bytes);

            // Guardar la imagen en la base de datos
            imageService.save(image);

            return ResponseEntity.status(HttpStatus.CREATED).body("Image added successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add image.");
        }
    }
}
