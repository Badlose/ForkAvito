package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;

public interface AdsService {

    Ads getAllAds();

    Ad createOrUpdateAd(MultipartFile image, CreateOrUpdateAd updateAd);

    ExtendedAd getAdById(Integer id);

    void removeAd(Integer id);

    Ad updateAds(Integer id, CreateOrUpdateAd createOrUpdateAd);

    Ads getAdsMe();

    String[] updateImage(Integer id, MultipartFile image);

}
