package com.nopay.nopayapi.service.users;

import com.nopay.nopayapi.entity.users.Seller;
import com.nopay.nopayapi.repository.users.SellerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    public List<Seller> findAll() {
        return sellerRepository.findAll();
    }

    public Optional<Seller> findById(Integer id) {
        return sellerRepository.findById(id);
    }

    public Seller save(Seller seller) {
        return sellerRepository.save(seller);
    }

    public void deleteById(Integer id) {
        sellerRepository.deleteById(id);
    }
}