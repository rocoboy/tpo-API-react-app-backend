package com.nopay.nopayapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nopay.nopayapi.entity.Image;
import com.nopay.nopayapi.repository.ImageRepository;
import java.util.Set;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public void save(Image image) {
        imageRepository.save(image);
    }

    @Override
    public void deleteById(Integer id) {
        imageRepository.deleteById(id);
    }

    @Override
    public void saveAll(Set<Image> images) {
        imageRepository.saveAll(images);
    }

}
