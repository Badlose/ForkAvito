package ru.skypro.homework.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.service.AdsService;

@Service
public class AdsServiceImpl implements AdsService {
    @Override
    public Ads getAllAds() {
        return null;
    }

    @Override
    public Ad createOrUpdateAd(MultipartFile image, CreateOrUpdateAd updateAd) {
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
    public Ad updateAds(Integer id, CreateOrUpdateAd createOrUpdateAd) {
        return null;
    }

    @Override
    public Ads getAdsMe() {
        return null;
    }

    @Override
    public ResponseEntity<?> updateImage(Integer id, MultipartFile image) {
        return null;
    }
}
