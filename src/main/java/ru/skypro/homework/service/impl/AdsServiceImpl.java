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

    /**
     * Get all ads
     * @return {@link Ads}
     */
    @Override
    @Transactional
    public Ads getAllAds() {
        List<AdEntity> adEntityList = adRepository.findAll();
        return toAds(adEntityList);
    }

    /**
     * Create new ad
     * @param userDetails Authorized user from CustomUserDetails
     * @param createAd Ad's data
     * @param image Ad's image
     * @return {@link Ad}
     */
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

    /**
     * Get Ad by id
     * @param id Ad`s id
     * @return {@link ExtendedAd}
     */
    @Override
    @Transactional
    public ExtendedAd getAdById(Integer id) {
        AdEntity entity = getAdEntity(id);
        return toExtendedAd(entity);
    }

    /**
     * Remove ad and ad`s image from DB
     * @param id Ad`s id
     */
    @Override
    @Transactional
    public void removeAd(Integer id) {
        validateAuthor(id);
        AdEntity adEntity = getAdEntity(id);
        String imageUri = adEntity.getImage();
        adRepository.deleteById(id);
        imageService.deleteImage(imageUri);
    }

    /**
     * Update ad
     * @param id Ad`s id
     * @param updateAd Ad's new data
     * @return {@link Ad}
     */
    @Override
    @Transactional
    public Ad updateAd(Integer id, CreateOrUpdateAd updateAd) {
        validateAuthor(id);
        AdEntity adEntity = getAdEntity(id);
        adEntity = toAdEntity(adEntity, updateAd);
        adRepository.save(adEntity);
        log.info("Ad with id {} was updated", id);
        return toAd(adEntity);
    }

    /**
     * Get all Authorized user ad`s
     * @param userDetails Authorized user from CustomUserDetails
     * @return {@link Ads}
     */
    @Override
    @Transactional
    public Ads getAdsMe(CustomUserDetails userDetails) {
        UserEntity userEntity = getUserEntity(userDetails);
        List<AdEntity> adEntityList = userEntity.getAds();
        return toAds(adEntityList);
    }

    /**
     * Update ad`s image
     * @param id Ad`s id
     * @param image new image
     * @return image byte[]
     */
    @Override
    @Transactional
    public byte[] updateImage(Integer id, MultipartFile image) {
        validateAuthor(id);
        AdEntity adEntity = getAdEntity(id);
        imageService.deleteImage(adEntity.getImage());
        String imageUri = imageService.uploadAdImage(image, id);
        log.info("Image for ad with id {} was updated", id);
        adEntity.setImage(imageUri);
        adRepository.save(adEntity);
        return imageService.getUpdatedImageBytes(imageUri);
    }

    /**
     * Get ad`s image
     * @param id image id
     * @return image byte[]
     */
    @Override
    @Transactional
    public byte[] getAdImage(String id) {
        return imageService.getAdsImageBytes(id);
    }

    /**
     * Validate ad`s author
     * @param id Ad`s id
     */
    private void validateAuthor(Integer id) {
        AdEntity adEntity = getAdEntity(id);
        if (!checkAuthority(adEntity)) {
            throw new AdAccessNotAllowedException(id);
        }
    }

    /**
     * Check user`s right to edit ad
     * @param adEntity ad form DB
     * @return {@code true} if user is ad`s author, <br>
     * {@code false} otherwise
     */
    private boolean checkAuthority(AdEntity adEntity) {
        UserEntity userEntity = getUserEntityFromAuthentication();
        return userEntity.getRole().equals(Role.ADMIN) ||
                userEntity.getId().equals(adEntity.getUser().getId());
    }

    /**
     * Get user entity from Authentication
     * @return {@link UserEntity}
     */
    private UserEntity getUserEntityFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    /**
     * Get Ad entity from DB
     * @param id Ad`s id
     * @return {@link AdEntity}
     */
    private AdEntity getAdEntity(Integer id) {
        return adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(id));
    }

    /**
     * Get User entity from DB
     * @param userDetails Authorized user from CustomUserDetails
     * @return {@link UserEntity}
     */
    private UserEntity getUserEntity(CustomUserDetails userDetails) {
        String username = userDetails.getUsername();
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    /**
     * Check Ad author in AdsController methods
     * @param id Ad`s id
     * @return {@code true} if user is ad`s author, <br>
     * {@code false} otherwise
     */
    public boolean checkAdAuthor(Integer id) {
        AdEntity adEntity = getAdEntity(id);
        UserEntity userEntity = getUserEntityFromAuthentication();
        Integer userId = userEntity.getId();
        Integer adId = adEntity.getUser().getId();
        return userId.equals(adId);
    }

}
