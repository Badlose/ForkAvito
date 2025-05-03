package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
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

import java.util.List;

import static ru.skypro.homework.mapper.AdMapper.*;

@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {

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

        AdEntity entity = createAdEntity(userEntity, createAd);
        entity.setUser(userEntity);

        entity.setImage(image.getName()); // логика работы с картинками

        adRepository.save(entity);
        return toAd(createAd, entity);
    }

    @Override
    @Transactional
    public ExtendedAd getAdById(Integer id) {
        AdEntity entity = adRepository.findByPk(id);
        return toExtendedAd(entity);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or (hasRole('ROLE_USER') and #id == getAdAuthorId(#id))")
    public void removeAd(CustomUserDetails userDetails, Integer id) {
        adRepository.deleteById(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or (hasRole('ROLE_USER') and #id == getAdAuthorId(#id))")
    public Ad updateAd(CustomUserDetails userDetails, Integer id, CreateOrUpdateAd updateAd) {

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
    public MultipartFile updateImage(CustomUserDetails userDetails, Integer id, MultipartFile image) {

        return null;
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

    private Integer getAdAuthorId(Integer id) {
        return getAdEntity(id).getUser().getId();
    }

}
