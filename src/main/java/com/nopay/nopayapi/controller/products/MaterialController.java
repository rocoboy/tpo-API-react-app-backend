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

import com.nopay.nopayapi.entity.products.Material;
import com.nopay.nopayapi.service.products.MaterialService;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @GetMapping
    public List<Material> findAll() {
        return materialService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Material> findById(@PathVariable Integer id) {
        Optional<Material> material = materialService.findById(id);
        return material.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Material save(@RequestBody Material material) {
        return materialService.save(material);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        materialService.deleteById(id);
    }
}