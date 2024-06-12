package com.nopay.nopayapi.service.users;

import com.nopay.nopayapi.entity.users.PaymentMethod;
import com.nopay.nopayapi.repository.users.PaymentMethodRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodsRepository;

    public List<PaymentMethod> findAll() {
        return paymentMethodsRepository.findAll();
    }

    public Optional<PaymentMethod> findById(Integer id) {
        return paymentMethodsRepository.findById(id);
    }

    public PaymentMethod save(PaymentMethod paymentMethods) {
        return paymentMethodsRepository.save(paymentMethods);
    }

    public void deleteById(Integer id) {
        paymentMethodsRepository.deleteById(id);
    }
}