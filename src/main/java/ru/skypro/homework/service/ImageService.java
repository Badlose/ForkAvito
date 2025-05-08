package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    String uploadUserImage(MultipartFile image, Integer id) throws IOException;

    String uploadAdImage(MultipartFile image, Integer id) throws IOException;

    byte[] getUsersImageBytes(String imageUrl);

    byte[] getAdsImageBytes(String imageUrl);

    byte[] getUpdatedImageBytes(String imageUrl);

//    String getMediaType(String imageUrl);
}
