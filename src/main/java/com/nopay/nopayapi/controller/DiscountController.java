package com.nopay.nopayapi.controller;

import com.nopay.nopayapi.dto.orders.DiscountCodeDTO;
import com.nopay.nopayapi.service.orders.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @GetMapping
    public ResponseEntity<List<DiscountCodeDTO>> getAllDiscountCodes() {
        List<DiscountCodeDTO> discountCodes = discountService.getAllDiscountCodes();
        return ResponseEntity.ok(discountCodes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountCodeDTO> getDiscountCodeById(@PathVariable Integer id) {
        DiscountCodeDTO discountCode = discountService.getDiscountCodeById(id);
        return ResponseEntity.ok(discountCode);
    }

    @PostMapping
    public ResponseEntity<DiscountCodeDTO> createDiscountCode(@RequestBody DiscountCodeDTO discountCodeDTO) {
        DiscountCodeDTO createdDiscountCode = discountService.createDiscountCode(discountCodeDTO);
        return ResponseEntity.ok(createdDiscountCode);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscountCodeDTO> updateDiscountCode(@PathVariable Integer id,
            @RequestBody DiscountCodeDTO discountCodeDTO) {
        DiscountCodeDTO updatedDiscountCode = discountService.updateDiscountCode(id, discountCodeDTO);
        return ResponseEntity.ok(updatedDiscountCode);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscountCode(@PathVariable Integer id) {
        discountService.deleteDiscountCode(id);
        return ResponseEntity.noContent().build();
    }
}
