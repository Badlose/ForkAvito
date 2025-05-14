package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.accept.CreateOrUpdateAd;
import ru.skypro.homework.dto.give.Ad;
import ru.skypro.homework.dto.give.Ads;
import ru.skypro.homework.dto.give.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exception.AdAccessNotAllowedException;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

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
    public Ad createNewAd(CustomUserDetails userDetails, CreateOrUpdateAd createAd, MultipartFile image) {
        UserEntity userEntity = getUserEntity(userDetails);
        AdEntity entity = toCreatedAdEntity(userEntity, createAd);
        entity.setUser(userEntity);
        adRepository.save(entity);
        log.info("New ad was created: {}", entity.getTitle());
        String imageUrl = imageService.uploadAdImage(image, entity.getPk());
        entity.setImage(imageUrl);
        return toAd(createAd, entity);
    }

    @Override
    @Transactional
    public ExtendedAd getAdById(Integer id) {
        AdEntity entity = getAdEntity(id);
        return toExtendedAd(entity);
    }

    @Override
    @Transactional
    public void removeAd(Integer id) {
        validateAuthor(id);
        adRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Ad updateAd(Integer id, CreateOrUpdateAd updateAd) {
//        validateAuthor(id);
        AdEntity adEntity = getAdEntity(id);
        adEntity = toAdEntity(adEntity, updateAd);
        adRepository.save(adEntity);
        log.info("Ad with id {} was updated", id);
        return toAd(adEntity);
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
    public byte[] updateImage(Integer id, MultipartFile image) {
        validateAuthor(id);
        AdEntity adEntity = getAdEntity(id);
        String imageUri = imageService.uploadAdImage(image, id);
        log.info("Image for ad with id {} was updated", id);
        adEntity.setImage(imageUri);
        adRepository.save(adEntity);
        return imageService.getUpdatedImageBytes(imageUri);
    }

    @Override
    @Transactional
    public byte[] getAdImage(String id) {
        return imageService.getAdsImageBytes(id);
    }

    private void validateAuthor(Integer id) {
        AdEntity adEntity = getAdEntity(id);
        if (!checkAuthority(adEntity)) {
            throw new AdAccessNotAllowedException(id);
        }
    }

    private boolean checkAuthority(AdEntity adEntity) {
        UserEntity userEntity = getUserEntityFromAuthentication();
        return userEntity.getRole().equals(Role.ADMIN) ||
                userEntity.getId().equals(adEntity.getUser().getId());
    }

    private UserEntity getUserEntityFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    private AdEntity getAdEntity(Integer id) {
        return adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(id));
    }


    private UserEntity getUserEntity(CustomUserDetails userDetails) {
        String username = userDetails.getUsername();
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public boolean checkAdAuthor(Integer id) {
        AdEntity adEntity = getAdEntity(id);
        UserEntity userEntity = getUserEntityFromAuthentication();
        Integer userId = userEntity.getId();
        Integer adId = adEntity.getUser().getId();
        return userId.equals(adId);
    }

}
