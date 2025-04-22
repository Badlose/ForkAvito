package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.accept.CreateOrUpdateAd;
import ru.skypro.homework.dto.give.Ad;
import ru.skypro.homework.dto.give.Ads;
import ru.skypro.homework.dto.give.ExtendedAd;

public interface AdsService {

    Ads getAllAds();

    Ad createNewAd(CreateOrUpdateAd updateAd, MultipartFile image);

    ExtendedAd getAdById(Integer id);

    void removeAd(Integer id);

    Ad updateAd(Integer id, CreateOrUpdateAd createOrUpdateAd);

    Ads getAdsMe();

    MultipartFile updateImage(Integer id, MultipartFile image);

}
