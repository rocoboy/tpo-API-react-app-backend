package com.nopay.nopayapi.service.products;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nopay.nopayapi.entity.products.Color;
import com.nopay.nopayapi.repository.products.ColorRepository;

@Service
public class ColorService {
    @Autowired
    private ColorRepository colorRepository;

    public List<Color> findAll() {
        return colorRepository.findAll();
    }

    public Optional<Color> findById(Integer id) {
        return colorRepository.findById(id);
    }

    public Color save(Color color) {
        return colorRepository.save(color);
    }

    public void deleteById(Integer id) {
        colorRepository.deleteById(id);
    }
}