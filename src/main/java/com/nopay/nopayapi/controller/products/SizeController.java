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

import com.nopay.nopayapi.entity.products.Size;
import com.nopay.nopayapi.service.products.SizeService;

@RestController
@RequestMapping("/api/sizes")
public class SizeController {
    @Autowired
    private SizeService sizeService;

    @GetMapping
    public List<Size> findAll() {
        return sizeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Size> findById(@PathVariable Integer id) {
        Optional<Size> size = sizeService.findById(id);
        return size.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Size save(@RequestBody Size size) {
        return sizeService.save(size);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        sizeService.deleteById(id);
    }
}