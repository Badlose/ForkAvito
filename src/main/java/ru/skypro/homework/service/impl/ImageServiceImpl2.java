package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.exception.ImageUploadException;
import ru.skypro.homework.exception.UnreadableImageException;
import ru.skypro.homework.service.ImageService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Slf4j
@Service
@Transactional
public class ImageServiceImpl2 implements ImageService {

    @Value("${path.to.users.image.folder}")
    private String usersImageDirectory;
    @Value("${path.to.ads.image.folder}")
    private String adsImageDirectory;

    @Override
    public String uploadUserImage(MultipartFile image, Integer id) {
//todo или лучше catch кастом исключение как в методе ниже?
        String userImageUri = usersImageDirectory + "/User_Id_" + id + "_" + UUID.randomUUID() + getExtension(image.getOriginalFilename());
        return saveImage(image, userImageUri);
    }

    @Override
    public String uploadAdImage(MultipartFile image, Integer id) {
//todo или лучше catch кастом исключение как в методе ниже?
        String adImageUri = adsImageDirectory + "/Ad_Id_" + id + "_" + UUID.randomUUID() + getExtension(image.getOriginalFilename());
        return saveImage(image, adImageUri);
    }

    private String saveImage(MultipartFile image, String imageUri) {
        Path filePath = Path.of(imageUri);
        try {
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);

            try (InputStream is = image.getInputStream();
                 OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                 BufferedInputStream bis = new BufferedInputStream(is);
                 BufferedOutputStream bos = new BufferedOutputStream(os)) {
                bis.transferTo(bos);
            }
            return imageUri;
        } catch (IOException e) {
            throw new ImageUploadException("Ошибка при сохранении изображения: " + filePath, e);
        }
    }


    @Override
    public byte[] getUsersImageBytes(String imageUrl) {
//        String realFilePath = "D:\\SkyPro\\Diplom\\users\\images\\" + imageUrl;
        Path filePath = Path.of(usersImageDirectory, imageUrl);
        return getBytes(filePath);

    }

    @Override
    public byte[] getAdsImageBytes(String imageUrl) {
        Path filePath = Path.of(adsImageDirectory, imageUrl);
        return getBytes(filePath);
    }

    private static byte[] getBytes(Path realPath) {
        try {
            if (!Files.exists(realPath)) {
                throw new UnreadableImageException("Image not found: " + realPath);
            }
            byte[] imageBytes = Files.readAllBytes(realPath);
            return imageBytes;
        } catch (IOException e) {
            throw new UnreadableImageException(
                    String.format("Impossible to read image, %s", e.getMessage()));
        }
    }

    @Override
    public byte[] getUpdatedImageBytes(String imageUrl) {
        Path filePath = Path.of(imageUrl);
        return getBytes(filePath);
    }

    private String getExtension(String originalFile) {
        return originalFile.substring(originalFile.lastIndexOf("."));
    }

}
