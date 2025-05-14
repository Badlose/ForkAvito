package ru.skypro.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.ImageServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static ru.skypro.homework.helper.TestHelper.*;

@SpringBootTest
@Transactional
public class ImageServiceTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private ImageServiceImpl service;

    @Value("${path.to.users.image.folder}")
    private String usersImageDirectory;
    @Value("${path.to.ads.image.folder}")
    private String adsImageDirectory;
    private final String parentFolder = "D:\\SkyPro\\Diplom\\";
    private final String realUserImagePath = "D:\\SkyPro\\Diplom\\users\\images\\User_Id_1.jpeg";
    private final String realAdImagePath = "D:\\SkyPro\\Diplom\\ads\\images\\Ad_Id_1.jpeg";

    @BeforeEach
//    @Transactional
    void setUpData() {
        UserEntity preparedUserEntity = createUserEntityBuilder()
                .id(null)
                .username("username")
                .build();
        userRepository.save(preparedUserEntity);

        AdEntity adEntity = createAdEntityBuilder()
                .pk(null)
                .user(preparedUserEntity)
                .build();
        adRepository.save(adEntity);

        preparedUserEntity.setAds(List.of(adEntity));

        CommentEntity commentEntity = createCommentEntityBuilder()
                .pk(null)
                .user(preparedUserEntity)
                .ad(adEntity)
                .build();
        commentRepository.save(commentEntity);
        adEntity.setComments(List.of(commentEntity));
    }


    @Test
    @Transactional
    void shouldUploadUserImage() {
        MultipartFile image = getMultipartFileSemiStub();
        UserEntity userEntity = userRepository.findByUsername("username").orElseThrow();
        Integer id = userEntity.getId();
        String oldImageUrl = userEntity.getImage();

        String s = service.uploadUserImage(image, id);
        userEntity.setImage(s);

        String newImageUrl = userEntity.getImage();

        Path realPath = Paths.get(parentFolder + newImageUrl);
        System.out.println(realPath);

        assertThat(newImageUrl.substring(0, newImageUrl.lastIndexOf("_") - 1)).isEqualTo("/users/images/User_Id_");
        assertThat(newImageUrl).isNotEqualTo(oldImageUrl);
    }

    @Test
    void shouldUploadAdImage() {
        MultipartFile image = getMultipartFileSemiStub();
        UserEntity userEntity = userRepository.findByUsername("username").orElseThrow();
        AdEntity adEntity = adRepository.findByUserId(userEntity.getId());
        String oldImageUrl = adEntity.getImage();
        Integer id = adEntity.getPk();

        String s = service.uploadAdImage(image, id);

        adEntity.setImage(s);
        String newImageUrl = adEntity.getImage();

        assertThat(newImageUrl.substring(0, newImageUrl.lastIndexOf("_" ) - 1)).isEqualTo("/ads/images/Ad_Id_");
        assertThat(newImageUrl).isNotEqualTo(oldImageUrl);
    }

//    @Test
//    void shouldGetUserImageBytes() throws IOException {
//        UserEntity userEntity = userRepository.findByUsername("username").orElseThrow();
//        userEntity.setImage("\\User_Id_1.jpeg");
//
//        byte[] userImageBytes = service.getUsersImageBytes(userEntity.getImage());
//        Path path = Path.of(realUserImagePath);
//        byte[] realImageBytes = Files.readAllBytes(path);
//
//        assertThat(userImageBytes).isEqualTo(realImageBytes);
//    }
//
//    @Test
//    void shouldGetAdsImageBytes() throws IOException {
//        UserEntity userEntity = userRepository.findByUsername("username").orElseThrow();
//        AdEntity adEntity = adRepository.findByUserId(userEntity.getId());
//        adEntity.setImage("\\Ad_Id_1.jpeg");
//
//
//        byte[] adImageBytes = service.getAdsImageBytes(adEntity.getImage());
//        Path path = Path.of(realAdImagePath);
//        byte[] realImageBytes = Files.readAllBytes(path);
//
//        assertThat(adImageBytes).isEqualTo(realImageBytes);
//    }

    @Test
    void shouldGetUpdatedImageBytes() throws IOException {
        UserEntity userEntity = userRepository.findByUsername("username").orElseThrow();
        AdEntity adEntity = adRepository.findByUserId(userEntity.getId());
        adEntity.setImage("ads\\images\\Ad_Id_1.jpeg");


        byte[] adImageBytes = service.getUpdatedImageBytes(adEntity.getImage());
        Path path = Path.of(realAdImagePath);
        byte[] realImageBytes = Files.readAllBytes(path);

        assertThat(adImageBytes).isEqualTo(realImageBytes);
    }


}
