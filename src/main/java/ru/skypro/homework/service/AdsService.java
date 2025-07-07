package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.accept.CreateOrUpdateAd;
import ru.skypro.homework.dto.give.Ad;
import ru.skypro.homework.dto.give.Ads;
import ru.skypro.homework.dto.give.ExtendedAd;
import ru.skypro.homework.security.CustomUserDetails;

public interface AdsService {

    Ads getAllAds();

    Ad createNewAd(CustomUserDetails userDetails, CreateOrUpdateAd updateAd, MultipartFile image);

    ExtendedAd getAdById(Integer id);

    void removeAd(Integer id);

    Ad updateAd(Integer id, CreateOrUpdateAd createOrUpdateAd);

    Ads getAdsMe(CustomUserDetails userDetails);

    byte[] updateImage(Integer id, MultipartFile image);

    byte[] getAdImage(String id);

}
