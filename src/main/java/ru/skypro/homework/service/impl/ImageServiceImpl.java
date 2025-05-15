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

    /**
     * Upload user`s image. The method prepares the URI for the user`s image
     * @param image new image
     * @param id user`s id
     * @return new image URL
     */
    @Override
    public String uploadUserImage(MultipartFile image, Integer id) {
        String userImageUri = usersImageDirectory + "/User_Id_" + id + "_" + UUID.randomUUID() + getExtension(image.getOriginalFilename());
        return saveImage(image, userImageUri);
    }

    /**
     * Upload ad`s image. The method prepares the URI for the ad`s image
     * @param image new image
     * @param id ad`s id
     * @return new image URL
     */
    @Override
    public String uploadAdImage(MultipartFile image, Integer id) {
        String adImageUri = adsImageDirectory + "/Ad_Id_" + id + "_" + UUID.randomUUID() + getExtension(image.getOriginalFilename());
        return saveImage(image, adImageUri);
    }

    /**
     * Get user`s image. The method converts string URI to filepath
     * @param imageUri image URi
     * @return image byte[]
     */
    @Override
    public byte[] getUsersImageBytes(String imageUri) {
        Path filePath = Path.of(usersImageDirectory, imageUri);
        return getBytes(filePath);

    }

    /**
     * Get ad`s image. The method converts string URI to filepath
     * @param imageUrl image URI
     * @return image byte[]
     */
    @Override
    public byte[] getAdsImageBytes(String imageUrl) {
        Path filePath = Path.of(adsImageDirectory, imageUrl);
        return getBytes(filePath);
    }

    /**
     * Get new ad`s image when update ad`s image
     * @param imageUrl image URL
     * @return image byte[]
     */
    @Override
    public byte[] getUpdatedImageBytes(String imageUrl) {
        Path filePath = Path.of(parentFolder + imageUrl);
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new UnreadableImageException(getStringShortFilePath(filePath));
        }
    }

    /**
     * Remove image from disk
     * @param imageUrl image URI
     */
    @Override
    public void deleteImage(String imageUrl) {
        Path filePath = Path.of(parentFolder + imageUrl);
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            throw new ImageNotFoundException(getStringShortFilePath(filePath));
        }
    }

    /**
     * Save image. The method saves image to disk
     * @param image image
     * @param imageUri image URI
     * @return String image URL
     */
    private String saveImage(MultipartFile image, String imageUri) {
        Path filePath = Path.of(parentFolder + imageUri);
        try {
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new FilePathCreationException(getStringShortFilePath(filePath));
        }
        try {
            image.transferTo(filePath);
        } catch (IOException e) {
            throw new ImageUploadException(getStringShortFilePath(filePath));
        }
        return imageUri;
    }

    /**
     * Get image. The method reads image bytes from filepath
     * @param realPath image real filepath
     * @return image byte[]
     */
    private byte[] getBytes(Path realPath) {
        try {
            checkImageExist(realPath);
            return Files.readAllBytes(Path.of(parentFolder + realPath));
        } catch (IOException e) {
            throw new UnreadableImageException(getStringShortFilePath(realPath));
        }
    }

    /**
     * Get image extension
     * @param originalFile image original filename
     * @return String image extension
     */
    private String getExtension(String originalFile) {
        return originalFile.substring(originalFile.lastIndexOf("."));
    }

    /**
     * The method verifies that the image exists
     * @param realPath image real filepath
     */
    private void checkImageExist(Path realPath) {
        if (!Files.exists(Path.of(parentFolder + realPath))) {
            throw new ImageNotFoundException(getStringShortFilePath(realPath));
        }
    }

    /**
     * The method gets image`s short filepath
     * @param filePath image real filepath
     */
    private String getStringShortFilePath(Path filePath) {
        return filePath.toString().substring(0, filePath.toString().lastIndexOf("."));
    }
}
