package com.nopay.nopayapi.service.products;

import com.nopay.nopayapi.entity.products.Material;
import com.nopay.nopayapi.repository.products.MaterialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    public List<Material> findAll() {
        return materialRepository.findAll();
    }

    public Optional<Material> findById(Integer id) {
        return materialRepository.findById(id);
    }

    public Material save(Material material) {
        return materialRepository.save(material);
    }

    public void deleteById(Integer id) {
        materialRepository.deleteById(id);
    }
}
