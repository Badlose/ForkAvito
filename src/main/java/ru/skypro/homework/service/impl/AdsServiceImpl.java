package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.accept.CreateOrUpdateAd;
import ru.skypro.homework.dto.give.Ad;
import ru.skypro.homework.dto.give.Ads;
import ru.skypro.homework.dto.give.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;
import java.util.List;

import static ru.skypro.homework.mapper.AdMapper.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {

    private final ImageService imageService;
    private final AdRepository adRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Ads getAllAds() {
        List<AdEntity> adEntityList = adRepository.findAll();
        return toAds(adEntityList);
    }

    @Override
    @Transactional
    public Ad createNewAd(CustomUserDetails userDetails, CreateOrUpdateAd createAd, MultipartFile image) throws IOException {

        UserEntity userEntity = getUserEntity(userDetails);

        AdEntity entity = toCreatedAdEntity(userEntity, createAd);
        entity.setUser(userEntity);
        adRepository.save(entity);

        String imageUrl = imageService.uploadAdImage(image, entity.getPk());
        entity.setImage("\\" + imageUrl);

        return toAd(createAd, entity);
    }

    @Override
    @Transactional
    public ExtendedAd getAdById(Integer id) {
        AdEntity entity = adRepository.findByPk(id).orElseThrow(() -> new AdNotFoundException(
                String.format("Ad %d not found", id))
        );
        ExtendedAd extendedAd = toExtendedAd(entity);

        log.info(extendedAd.getImage());

        return extendedAd;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))") // and #id == getAdAuthorId(#id))
    public void removeAd(Integer id) {
        adRepository.deleteById(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))") //and checkAdHasSuchAuthor() == true)
    public Ad updateAd(Integer id, CreateOrUpdateAd updateAd) {

        AdEntity adFromDB = getAdEntity(id);

        adFromDB = toAdEntity(adFromDB, updateAd);
        adRepository.save(adFromDB);

        return toAd(adFromDB);
    }

    @Override
    @Transactional
    public Ads getAdsMe(CustomUserDetails userDetails) {
        UserEntity userEntity = getUserEntity(userDetails);
        List<AdEntity> adEntityList = userEntity.getAds();
        return toAds(adEntityList);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))") //and checkAdHasSuchAuthor() == true)
    public byte[] updateImage(Integer id, MultipartFile image) throws IOException {

        AdEntity adEntity = getAdEntity(id);
        String imageUrl = imageService.uploadAdImage(image, id);

        adEntity.setImage("\\" + imageUrl);

        adRepository.save(adEntity);

//        byte[] imageBytes;
//        try {
//            imageBytes = image.getBytes();
//        } catch (IOException e) {
//            throw new IncorrectImageException("Incorrect image");
//        }

        return imageService.getUpdatedImageBytes(imageUrl);
    }

    @Override
    @Transactional
    public byte[] getAdImage(String id) {
        return imageService.getAdsImageBytes(id);
    }

    private AdEntity getAdEntity(Integer id) {
        return adRepository.findById(id)
                .orElseThrow(() -> new AdNotFoundException(String.format("Ad %d not found", id)));
    }


    private UserEntity getUserEntity(CustomUserDetails userDetails) {
        String username = userDetails.getUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User %s not found", username)));
    }

    private boolean checkAdHasSuchAuthor(Integer id) { //todo может есть способ проще?
        AdEntity adEntity = adRepository.findById(id)
                .orElseThrow(() -> new AdNotFoundException(String.format("Ad %d not found", id)));
        UserEntity userEntity = adEntity.getUser();
        List<AdEntity> adEntityList = userEntity.getAds();
        return adEntityList.contains(adEntity);
    }

}
