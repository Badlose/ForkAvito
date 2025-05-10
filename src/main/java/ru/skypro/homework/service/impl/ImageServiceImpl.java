//package ru.skypro.homework.service.impl;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//import ru.skypro.homework.exception.UnreadableImageException;
//import ru.skypro.homework.repository.UserRepository;
//import ru.skypro.homework.service.ImageService;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.UUID;
//
//import static java.nio.file.StandardOpenOption.CREATE_NEW;
//
//@Slf4j
//@Service
//@Transactional
//public class ImageServiceImpl implements ImageService {
//
//    @Value("${path.to.users.image.folder}")
//    private String usersImageDirectory;
//    @Value("${path.to.ads.image.folder}")
//    private String adsImageDirectory;
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public String uploadUserImage(MultipartFile image, Integer id) throws IOException {
////todo или лучше catch кастом исключение как в методе ниже?
//
//        String fileName = "User_Id_" + id + getExtension(image.getOriginalFilename());
//        Path filePath = Path.of(usersImageDirectory, fileName);
//        return saveImage(image, filePath);
//    }
//
//    @Override
//    public String uploadAdImage(MultipartFile image, Integer id) throws IOException {
////todo или лучше catch кастом исключение как в методе ниже?
//
//        String fileName = "Ad_Id_" + id + "_"  + UUID.randomUUID() + getExtension(image.getOriginalFilename());
//        Path filePath = Path.of(adsImageDirectory, fileName);
//        return saveImage(image, filePath);
//    }
//
//    private String saveImage(MultipartFile image, Path filePath) throws IOException {
//
//        Files.createDirectories(filePath.getParent());
//        Files.deleteIfExists(filePath);
//
//        try (InputStream is = image.getInputStream();
//             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
//             BufferedInputStream bis = new BufferedInputStream(is);
//             BufferedOutputStream bos = new BufferedOutputStream(os)) {
//            bis.transferTo(bos);
//        }
////        log.info("UPLOAD image " + filePath);
//        return filePath.toString();
//    }
//
//    @Override
//    public byte[] getUsersImageBytes(String imageUrl) {
//////        Path imagePath = Paths.get(imageUrl);
////
//////        try (InputStream is = Files.newInputStream(imagePath);
//////             BufferedInputStream bis = new BufferedInputStream(is);
//////             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
//////            bis.transferTo(baos);
//////            return baos.toByteArray();
//////        } catch (IOException e) {
//////            throw new IncorrectImageException("Incorrect image");
//////        }
////
////
////        try {
//////            return Files.readAllBytes(imagePath);
//////            Path imagePath = Paths.get(imageUrl);
////
////            String realFilePath = "D:\\SkyPro\\Diplom\\users\\images\\" + imageUrl;
////            Path pathFromReal = Paths.get(realFilePath);
////
////            if (!Files.exists(pathFromReal)) {
////                throw new UnreadableImageException("Image not found: " + imageUrl);
////            }
////            byte[] imageBytes = Files.readAllBytes(pathFromReal);
////            return imageBytes;
////        } catch (IOException e) {
////            throw new UnreadableImageException(
////                    String.format("Impossible to read image, %s", e.getMessage()));
////        }
//        String realFilePath = "D:\\SkyPro\\Diplom\\users\\images\\" + imageUrl;
//        Path realPath = Paths.get(realFilePath);
//        return getBytes(realPath);
//    }
//
//    @Override
//    public byte[] getAdsImageBytes(String imageUrl) {
////        log.info("WE ARE HEREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE" + "getAdsImageBytes"+ "imageUrl");
////        String realFilePath = "D:\\SkyPro\\Diplom\\" + imageUrl;
//        String realFilePath = "D:\\SkyPro\\Diplom\\ads\\images\\" + imageUrl;
//
//        Path realPath = Paths.get(realFilePath);
//        return getBytes(realPath);
//    }
//
//    private static byte[] getBytes(Path realPath) {
////        log.info("REAL PATH " + realPath);
//        try {
//            if (!Files.exists(realPath)) {
//                throw new UnreadableImageException("Image not found: " + realPath);
//            }
//            byte[] imageBytes = Files.readAllBytes(realPath);
//            return imageBytes;
//        } catch (IOException e) {
//            throw new UnreadableImageException(
//                    String.format("Impossible to read image, %s", e.getMessage()));
//        }
//    }
//
//    @Override
//    public byte[] getUpdatedImageBytes(String imageUrl) {
//        String preparedString = "D:\\SkyPro\\Diplom\\" + imageUrl;
//        Path realPath = Paths.get(preparedString);
////        log.info("               getUpdatedImageBytes            " + realPath);
//        return getBytes(realPath);
//    }
//
////    @Override
////    public String getMediaType(String imageUrl) {
////        String lowerCasedName = imageUrl.toLowerCase();
////        if (lowerCasedName.endsWith(".jpg") || lowerCasedName.endsWith(".jpeg")) {
////            return "image/jpeg";
////        } else if (lowerCasedName.endsWith(".png")) {
////            return "image/png";
////        } else if (lowerCasedName.endsWith(".gif")) {
////            return "image/gif";
////        }
////        throw new IncorrectMediaTypeException();
////    }
//
//    private String getExtension(String originalFile) {
//        return originalFile.substring(originalFile.lastIndexOf("."));
//    }
//
//}
