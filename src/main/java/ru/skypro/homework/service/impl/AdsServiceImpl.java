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
import ru.skypro.homework.exception.AccessNotAllowedException;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.ImageUploadException;
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
    public Ad createNewAd(CustomUserDetails userDetails, CreateOrUpdateAd createAd, MultipartFile image) {
        UserEntity userEntity = getUserEntity(userDetails);
        AdEntity entity = toCreatedAdEntity(userEntity, createAd);
        entity.setUser(userEntity);
        adRepository.save(entity);
        String imageUrl = imageService.uploadAdImage(image, entity.getPk());
        entity.setImage("/" + imageUrl);
        return toAd(createAd, entity);
    }

    @Override
    @Transactional
    public ExtendedAd getAdById(Integer id) {
        AdEntity entity = adRepository.findByPk(id).orElseThrow(() -> new AdNotFoundException(
                String.format("Ad %d not found", id))
        );
        return toExtendedAd(entity);
    }

    @Override
    @Transactional
    public void removeAd(Integer id) { //todo я  видел preAuthorize в сервисных классах
        AdEntity adEntity = getAdEntity(id);
        if (checkAuthority(adEntity)) {
            adRepository.deleteById(id);
        } else {
            throw new AccessNotAllowedException("Вы не имеете права редактировать это объявление.");
        }
//        UserEntity userEntity = getUserEntityFromAuthentication();
//        if (userEntity.getRole().equals(Role.ADMIN)) {
//            adRepository.deleteById(id);
//        }
//
//        //todo есть смысл пытаться разделять? чтоб не создавались объекты впустую
//
//        AdEntity adEntity = getAdEntity(id);
//
//        if (userEntity.getId().equals(adEntity.getUser().getId())) {
//            adRepository.deleteById(id);
//        } else {
//            throw new AccessNotAllowedException("Вы не имеете права удалять это объявление.");
//        }

    }

    @Override
    @Transactional
    public Ad updateAd(Integer id, CreateOrUpdateAd updateAd) {
        AdEntity adEntity = getAdEntity(id);
        if (checkAuthority(adEntity)) {
            adEntity = toAdEntity(adEntity, updateAd);
            adRepository.save(adEntity);
            return toAd(adEntity);
        } else {
            throw new AccessNotAllowedException("Вы не имеете права редактировать это объявление.");
        }
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
        AdEntity adEntity = getAdEntity(id);
        if (checkAuthority(adEntity)) {
            String imageUrl = imageService.uploadAdImage(image, id);
            adEntity.setImage("/" + imageUrl);
            adRepository.save(adEntity);
            return imageService.getUpdatedImageBytes(imageUrl);
        } else {
            throw new AccessNotAllowedException("Вы не имеете права редактировать изображение этого объявления.");
        }
//        UserEntity userEntity = getUserEntityFromAuthentication();
//        AdEntity adEntity = getAdEntity(id);
//
//        if (userEntity.getRole().equals(Role.ADMIN)) {
//            String imageUrl = imageService.uploadAdImage(image, id);
//            adEntity.setImage("/" + imageUrl);
//            adRepository.save(adEntity);
//            return imageService.getUpdatedImageBytes(imageUrl);
//        }
//        if (userEntity.getId().equals(adEntity.getUser().getId())) {
//            String imageUrl = imageService.uploadAdImage(image, id);
//            adEntity.setImage("/" + imageUrl);
//            adRepository.save(adEntity);
//            return imageService.getUpdatedImageBytes(imageUrl);
//        } else {
//            throw new AccessNotAllowedException("Вы не имеете права редактировать это объявление.");
//        }
    }

    @Override
    @Transactional
    public byte[] getAdImage(String id) {
        return imageService.getAdsImageBytes(id);
    }

    private boolean checkAuthority(AdEntity adEntity) {
        UserEntity userEntity = getUserEntityFromAuthentication();
        return userEntity.getRole().equals(Role.ADMIN) ||
                userEntity.getId().equals(adEntity.getUser().getId());
    }

    private UserEntity getUserEntityFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User %s not found", username)));
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
