package com.nopay.nopayapi.service;

import com.nopay.nopayapi.entity.Image;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public interface ImageService {
    public Image create(Image image);

    public Optional<Image> findById(Integer id);

    public void save(Image image);

    public void deleteById(Integer id);

    public void saveAll(Set<Image> images);

    public List<Image> getAllImages();
}