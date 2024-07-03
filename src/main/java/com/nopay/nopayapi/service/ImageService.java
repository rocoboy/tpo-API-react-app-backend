package com.nopay.nopayapi.service;

import com.nopay.nopayapi.entity.Image;

import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public interface ImageService {

    public void save(Image image);

    public void deleteById(Integer id);

    public void saveAll(Set<Image> images);

}