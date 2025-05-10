package ru.skypro.homework.service.Unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.NewTypeTesting.TestHelper;
import ru.skypro.homework.dto.accept.CreateOrUpdateAd;
import ru.skypro.homework.dto.give.Ad;
import ru.skypro.homework.dto.give.Ads;
import ru.skypro.homework.dto.give.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.impl.AdsServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.skypro.NewTypeTesting.TestHelper.*;

@ExtendWith(MockitoExtension.class)
public class AdServiceImplUnitTest {
    @Mock
    private AdRepository adRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ImageService imageService;
    @InjectMocks
    private AdsServiceImpl service;

    @Test
    void shouldGetAllAds() {
        AdEntity adEntity = createAdEntityBuilder()
                .pk(1)
                .user(createUserEntity())
                .build();
        List<AdEntity> adEntityList = List.of(adEntity);

        when(adRepository.findAll()).thenReturn(adEntityList);

        Ads ads = service.getAllAds();

        verify(adRepository, times(1)).findAll();
        Ad adToVerify = ads.getResults().get(0);
        assertThat(adToVerify.getAuthor()).isEqualTo(adEntity.getUser().getId());
        assertThat(adToVerify.getTitle()).isEqualTo(adEntity.getTitle());
        assertThat(adToVerify.getPrice()).isEqualTo(adEntity.getPrice());
    }

    @Test
    void shouldCreateNewAd() throws IOException {
        MultipartFile image = getMultipartFileSemiStub();
        UserEntity userEntity = getUserEntity();
        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
        CreateOrUpdateAd createAd = getCreateOrUpdateAd();
        AdEntity adEntity = AdMapper.toCreatedAdEntity(userEntity,createAd);
        adEntity.setUser(userEntity);
        adEntity.setImage("//ads/images/stub");

        when(adRepository.save(any(AdEntity.class))).thenReturn(adEntity);
        when(imageService.uploadAdImage(image, adEntity.getPk())).thenReturn("/ads/images/stub");
        when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.of(userEntity));

        Ad addedAd = service.createNewAd(userDetails, createAd, image);

        verify(adRepository, times(1)).save(adEntity);
        assertThat(addedAd.getAuthor()).isEqualTo(adEntity.getUser().getId());
        assertThat(addedAd.getTitle()).isEqualTo(adEntity.getTitle());
        assertThat(addedAd.getPrice()).isEqualTo(adEntity.getPrice());
    }

    @Test
    void shouldGetAdById() {
        UserEntity userEntity = createUserEntity();
        AdEntity adEntity = createAdEntityBuilder()
                .pk(1)
                .user(userEntity)
                .build();
        Integer adId = adEntity.getPk();

        when(adRepository.findByPk(adId)).thenReturn(Optional.of(adEntity));

        ExtendedAd extendedAd = service.getAdById(adId);

        verify(adRepository, times(1)).findByPk(adId);
        assertThat(extendedAd.getEmail()).isEqualTo(adEntity.getUser().getUsername());

    }

    @Test
    void shouldRemoveAd() {
        UserEntity userEntity = createUserEntity();
        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
        AdEntity adEntity = createAdEntityBuilder()
                .pk(1)
                .user(userEntity)
                .build();
        Integer adId = adEntity.getPk();
        Authentication authentication = new TestAuthentication(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.of(userEntity));
        when(adRepository.findById(adId)).thenReturn(Optional.of(adEntity));
        doNothing().when(adRepository).deleteById(adId);

        service.removeAd(adId);

        verify(adRepository, times(1)).deleteById(adId);
    }

    @Test
    void shouldUpdateAd() {
        UserEntity userEntity = createUserEntity();
        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
        Authentication authentication = new TestAuthentication(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AdEntity adEntity = createAdEntityBuilder()
                .pk(1)
                .user(userEntity)
                .build();
        Integer adId = adEntity.getPk();
        CreateOrUpdateAd updateAd = getCreateOrUpdateAd();
        AdEntity adEntityBefore = AdMapper.toAdEntity(adEntity, updateAd);
        Ad adBefore = AdMapper.toAd(adEntityBefore);

        when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.of(userEntity));
        when(adRepository.findById(adId)).thenReturn(Optional.of(adEntity));
        when(adRepository.save(adEntityBefore)).thenReturn(adEntityBefore);

        Ad ad = service.updateAd(adId, updateAd);

        verify(adRepository, times(1)).save(adEntityBefore);
        assertThat(ad.getTitle()).isEqualTo(adBefore.getTitle());
        assertThat(ad.getPrice()).isEqualTo(adBefore.getPrice());
    }

//    @Test todo метод без вызова репозитория
//    void shouldGetAdsMe() {
//        UserEntity userEntity = createUserEntity();
//        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
//        AdEntity adEntity = createAdEntityBuilder()
//                .pk(1)
//                .user(userEntity)
//                .build();
//        Integer adId = adEntity.getPk();
//
//        when()
//    }
}
