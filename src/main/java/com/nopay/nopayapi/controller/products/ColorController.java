package com.nopay.nopayapi.controller.products;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nopay.nopayapi.entity.products.Color;
import com.nopay.nopayapi.service.products.ColorService;

@RestController
@RequestMapping("/api/colors")
public class ColorController {
    @Autowired
    private ColorService colorService;

    @GetMapping
    public List<Color> findAll() {
        return colorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Color> findById(@PathVariable Integer id) {
        Optional<Color> color = colorService.findById(id);
        return color.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Color save(@RequestBody Color color) {
        return colorService.save(color);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        colorService.deleteById(id);
    }
}