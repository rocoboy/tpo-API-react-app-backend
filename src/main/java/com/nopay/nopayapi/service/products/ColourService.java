package com.nopay.nopayapi.service.products;

import com.nopay.nopayapi.entity.products.Colour;
import com.nopay.nopayapi.repository.products.ColourRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColourService {

    @Autowired
    private ColourRepository colourRepository;

    public List<Colour> findAll() {
        return colourRepository.findAll();
    }

    public Optional<Colour> findById(long id) {
        return colourRepository.findById(id);
    }

    public Colour save(Colour colour) {
        return colourRepository.save(colour);
    }

    public void deleteById(long id) {
        colourRepository.deleteById(id);
    }
}
