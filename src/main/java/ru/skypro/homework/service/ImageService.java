package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadUserImage(MultipartFile image, Integer id);

    String uploadAdImage(MultipartFile image, Integer id);

    byte[] getUsersImageBytes(String imageUrl);

    byte[] getAdsImageBytes(String imageUrl);

    byte[] getUpdatedImageBytes(String imageUrl);

    void deleteImage(String imageUrl);

}
