package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.exception.FilePathCreationException;
import ru.skypro.homework.exception.ImageNotFoundException;
import ru.skypro.homework.exception.ImageUploadException;
import ru.skypro.homework.exception.UnreadableImageException;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    @Value("${path.to.users.image.folder}")
    private String usersImageDirectory;
    @Value("${path.to.ads.image.folder}")
    private String adsImageDirectory;
    @Value("${path.to.parent.folder}")
    private String parentFolder;

    @Override
    public String uploadUserImage(MultipartFile image, Integer id) {
        String userImageUri = usersImageDirectory + "/User_Id_" + id + "_" + UUID.randomUUID() + getExtension(image.getOriginalFilename());
        return saveImage(image, userImageUri);
    }

    @Override
    public String uploadAdImage(MultipartFile image, Integer id) {
        String adImageUri = adsImageDirectory + "/Ad_Id_" + id + "_" + UUID.randomUUID() + getExtension(image.getOriginalFilename());
        return saveImage(image, adImageUri);
    }

    @Override
    public byte[] getUsersImageBytes(String imageUrl) {
        Path filePath = Path.of(usersImageDirectory, imageUrl);
        return getBytes(filePath);

    }

    @Override
    public byte[] getAdsImageBytes(String imageUrl) {
        Path filePath = Path.of(adsImageDirectory, imageUrl);
        return getBytes(filePath);
    }

    @Override
    public byte[] getUpdatedImageBytes(String imageUrl) {
        Path filePath = Path.of(parentFolder + imageUrl);
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new UnreadableImageException(getStringSortFilePath(filePath));
        }
    }

    @Override
    public void deleteImage(String imageUrl) {
        Path filePath = Path.of(parentFolder + imageUrl);
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            throw new ImageNotFoundException(getStringSortFilePath(filePath));
        }
    }

    private String saveImage(MultipartFile image, String imageUri) {
        Path filePath = Path.of(parentFolder + imageUri);
        try {
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new FilePathCreationException(getStringSortFilePath(filePath));
        }
        try {
            image.transferTo(filePath);
        } catch (IOException e) {
            throw new ImageUploadException(getStringSortFilePath(filePath));
        }
        return imageUri;
    }


    private byte[] getBytes(Path realPath) {
        try {
            checkImageExist(realPath);
            return Files.readAllBytes(Path.of(parentFolder + realPath));
        } catch (IOException e) {
            throw new UnreadableImageException(getStringSortFilePath(realPath));
        }
    }

    private String getExtension(String originalFile) {
        return originalFile.substring(originalFile.lastIndexOf("."));
    }

    private void checkImageExist(Path realPath) {
        if (!Files.exists(Path.of(parentFolder + realPath))) {
            throw new ImageNotFoundException(getStringSortFilePath(realPath));
        }
    }

    private String getStringSortFilePath(Path filePath) {
        return filePath.toString().substring(0, filePath.toString().lastIndexOf("."));
    }
}
