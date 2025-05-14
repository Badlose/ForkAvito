package ru.skypro.homework.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.accept.CreateOrUpdateAd;
import ru.skypro.homework.dto.give.Ad;
import ru.skypro.homework.dto.give.Ads;
import ru.skypro.homework.dto.give.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.service.impl.AdsServiceImpl;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.skypro.homework.helper.TestHelper.*;

@SpringBootTest
public class AdServiceImplTest {
    @Autowired
    private AdRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdsServiceImpl service;

    @BeforeEach
    @Transactional
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
        repository.save(adEntity);
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldGetAllAds() {
        UserEntity userEntity = userRepository.findByUsername("username").orElseThrow();

        AdEntity adEntity = createAdEntityBuilder()
                .pk(null)
                .user(userEntity)
                .build();

        repository.save(adEntity);

        Ads adsFromDb = service.getAllAds();
        Ad adFromDb = adsFromDb.getResults().get(0);

        assertThat(adFromDb.getAuthor()).isEqualTo(adEntity.getUser().getId());
    }

    @Test
    @Transactional
    void shouldCreateNewAd() throws IOException {
        UserEntity userEntity = userRepository.findByUsername("username").orElseThrow();
        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
        CreateOrUpdateAd createdAd = getCreateOrUpdateAd();
        MultipartFile image = getMultipartFileSemiStub();

        Ad adFromDb = service.createNewAd(userDetails, createdAd, image);

        assertThat(adFromDb.getPrice()).isEqualTo(createdAd.getPrice());
        assertThat(adFromDb.getTitle()).isEqualTo(createdAd.getTitle());
    }

    @Test
    @Transactional
    void shouldGetAdById() {
        UserEntity userEntity = userRepository.findByUsername("username").orElseThrow();
        AdEntity adEntity = repository.findByUserId(userEntity.getId());

        ExtendedAd extendedAdFromDb = service.getAdById(adEntity.getPk());

        assertThat(extendedAdFromDb).isNotNull();
        assertThat(extendedAdFromDb.getPk()).isEqualTo(adEntity.getPk());
        assertThat(extendedAdFromDb.getAuthorFirstName()).isEqualTo(adEntity.getUser().getFirstName());
        assertThat(extendedAdFromDb.getAuthorLastName()).isEqualTo(adEntity.getUser().getLastName());
        assertThat(extendedAdFromDb.getDescription()).isEqualTo(adEntity.getDescription());
        assertThat(extendedAdFromDb.getEmail()).isEqualTo(adEntity.getUser().getUsername());
        assertThat(extendedAdFromDb.getImage()).isEqualTo(adEntity.getImage());
        assertThat(extendedAdFromDb.getPrice()).isEqualTo(adEntity.getPrice());
        assertThat(extendedAdFromDb.getTitle()).isEqualTo(adEntity.getTitle());
    }

    @Test
    void shouldUpdateAd() {
        UserEntity userEntity = userRepository.findByUsername("username").orElseThrow();
        AdEntity adEntity = repository.findByUserId(userEntity.getId());
        CreateOrUpdateAd updateAd = getCreateOrUpdateAd();

        Ad adFromDb = service.updateAd(adEntity.getPk(), updateAd);

        assertThat(adFromDb).isNotNull();
        assertThat(adFromDb.getAuthor()).isEqualTo(adEntity.getUser().getId());
        assertThat(adFromDb.getImage()).isEqualTo(adEntity.getImage());
        assertThat(adFromDb.getPk()).isEqualTo(adEntity.getPk());
        assertThat(adFromDb.getPrice()).isEqualTo(updateAd.getPrice());
        assertThat(adFromDb.getTitle()).isEqualTo(updateAd.getTitle());
    }

    @Test
    void shouldGetAdsMe() {
        UserEntity userEntity = userRepository.findByUsername("username").orElseThrow();
        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
        AdEntity adEntity = repository.findByUserId(userEntity.getId());

        Ads adsFromDb = service.getAdsMe(userDetails);

        Ad mappedAd = adsFromDb.getResults().get(0);

        assertThat(mappedAd.getAuthor()).isEqualTo(adEntity.getUser().getId());
        assertThat(mappedAd.getImage()).isEqualTo(adEntity.getImage());
        assertThat(mappedAd.getPk()).isEqualTo(adEntity.getPk());
        assertThat(mappedAd.getPrice()).isEqualTo(adEntity.getPrice());
        assertThat(mappedAd.getTitle()).isEqualTo(adEntity.getTitle());

    }

    @Test
    @Transactional
    void shouldRemoveAd() {
        UserEntity userEntity = userRepository.findByUsername("username").orElseThrow();
        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
        Authentication authentication = new TestAuthentication(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AdEntity adEntity = repository.findByUserId(userEntity.getId());
        Integer adId = adEntity.getPk();

        service.removeAd(adId);

        assertThat(repository.existsById(adId)).isFalse();
    }

}
