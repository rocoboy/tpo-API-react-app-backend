package com.nopay.nopayapi.controller.users;

import com.nopay.nopayapi.entity.users.PaymentMethod;
import com.nopay.nopayapi.service.users.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payment-method")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    @GetMapping
    public List<PaymentMethod> getAllMethods() {
        return paymentMethodService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethod> getPaymentMethodById(@PathVariable Long id) {
        Optional<PaymentMethod> paymentMethod = paymentMethodService.findById(id);
        return paymentMethod.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public PaymentMethod createPaymentMethod(@RequestBody PaymentMethod paymentMethod) {
        return paymentMethodService.save(paymentMethod);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethod> updatePaymentMethod(@PathVariable Long id,
            @RequestBody PaymentMethod paymentMethodDetails) {
        Optional<PaymentMethod> paymentMethod = paymentMethodService.findById(id);
        if (paymentMethod.isPresent()) {
            PaymentMethod updatedPaymentMethod = paymentMethod.get();
            updatedPaymentMethod.setDues(paymentMethodDetails.getDues());
            updatedPaymentMethod.setDetailType(paymentMethodDetails.getDetailType());
            updatedPaymentMethod.setCardIusuer(paymentMethodDetails.getCardIusuer());
            updatedPaymentMethod.setInterest(paymentMethodDetails.getInterest());
            updatedPaymentMethod.setIdSeller(paymentMethodDetails.getIdSeller());
            return ResponseEntity.ok(paymentMethodService.save(updatedPaymentMethod));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> detelePaymentMethod(@PathVariable Long id) {
        paymentMethodService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}