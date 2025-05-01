package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.accept.CreateOrUpdateAd;
import ru.skypro.homework.dto.give.Ad;
import ru.skypro.homework.dto.give.Ads;
import ru.skypro.homework.dto.give.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.service.AdsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdMapper adMapper;


    @Override
    @Transactional
    public Ads getAllAds() {

        List<AdEntity> adEntityList = adRepository.findAll();

        Integer count = adEntityList.size();

        return AdMapper.staticToAds(count, adEntityList);
    }

    @Override
    @Transactional
    public Ad createNewAd(CustomUserDetails userDetails, CreateOrUpdateAd createAd, MultipartFile image) {

        String username = userDetails.getUsername();
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User {} not found", username)));

        AdEntity entity = new AdEntity();

        adMapper.toAdEntity(userEntity, createAd, entity);

        entity.setUser(userEntity);

        entity.setImage(image.getName()); // логика работы с картинками

        adRepository.save(entity);

        Ad ad = new Ad();

        adMapper.toAd(entity, ad);

        return ad;
    }

    @Override
    @Transactional
    public ExtendedAd getAdById(Integer id) {
        AdEntity entity = adRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException.AdNotFoundException(
                        String.format("Ad with id {} not found", id)
                ));

        Integer authorId = entity.getAuthor();

        UserEntity userEntity = userRepository.findById(authorId)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found for Ad id {}", id)));

        ExtendedAd extendedAd = new ExtendedAd();

        adMapper.toExtendedAd(userEntity, entity, extendedAd);

        return extendedAd;
    }

    @Override
    @Transactional
    public void removeAd(CustomUserDetails userDetails, Integer id) {

        String username = userDetails.getUsername();

        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found."));

        List<AdEntity> ads = userEntity.getAds();

        AdEntity adFromDB = adRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found for Ad id {}", id)));

        if (ads.contains(adFromDB)) {
            adRepository.deleteById(id);
        }


        // а кто вообще-то может уджалять??
    }

    @Override
    @Transactional
    public Ad updateAd(CustomUserDetails userDetails, Integer id, CreateOrUpdateAd updateAd) {

        String username = userDetails.getUsername();

        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found."));

        List<AdEntity> ads = userEntity.getAds();

        AdEntity adFromDB = adRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found for Ad id {}", id)));

        if (ads.contains(adFromDB)) {

            adMapper.toAdEntity(userEntity, updateAd, adFromDB);

            adRepository.save(adFromDB);
        } else {
            throw new RuntimeException();       // дописать
        }

        Ad adToReturn = new Ad();

        adMapper.toAd(updateAd, adFromDB, adToReturn);

        return adToReturn;

    }

    @Override
    @Transactional
    public Ads getAdsMe(CustomUserDetails userDetails) {

        String username = userDetails.getUsername();

        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found."));

        Integer userId = userEntity.getId();

        List<AdEntity> adEntityList = adRepository.findByUserId(userId);

        Integer count = adEntityList.size();

        return AdMapper.staticToAds(count, adEntityList);
    }

    @Override
    @Transactional
    public MultipartFile updateImage(CustomUserDetails userDetails, Integer id, MultipartFile image) {
        return null;
    }

}
