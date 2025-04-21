package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.give.Ad;
import ru.skypro.homework.dto.give.Ads;
import ru.skypro.homework.dto.accept.CreateOrUpdateAd;
import ru.skypro.homework.dto.give.ExtendedAd;
import ru.skypro.homework.service.AdsService;

@Service
public class AdsServiceImpl implements AdsService {

    @Override
    public Ads getAllAds() {
        return null;
    }

    @Override
    public Ad createNewAd(CreateOrUpdateAd updateAd, MultipartFile image) {
        return null;
    }

    @Override
    public ExtendedAd getAdById(Integer id) {
        return null;
    }

    @Override
    public void removeAd(Integer id) {
    }

    @Override
    public Ad updateAd(Integer id, CreateOrUpdateAd createOrUpdateAd) {
        return null;
    }

    @Override
    public Ads getAdsMe() {
        return null;
    }

    @Override
    public MultipartFile updateImage(Integer id, MultipartFile image) {
        return null;
    }

}
