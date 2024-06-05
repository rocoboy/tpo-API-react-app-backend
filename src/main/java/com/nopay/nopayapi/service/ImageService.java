package com.nopay.nopayapi.service;

import com.nopay.nopayapi.entity.Image;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ImageService {
    public Image create(Image image);

    public Image viewById(long id);

    public void save(Image image);

    public List<Image> getAllImages();
}
