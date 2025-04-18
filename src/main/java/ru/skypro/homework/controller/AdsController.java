package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.service.AdsService;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdsController {

    private AdsService adsService;

    @GetMapping
    public Ads getAllAds() {
        return adsService.getAllAds();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Ad addAd(@RequestBody MultipartFile image, CreateOrUpdateAd updateAd) {
        return adsService.createOrUpdateAd(image, updateAd);
    }

    @GetMapping("{id}")
    public ExtendedAd getAds(@RequestParam(required = true) Integer id) {
        return adsService.getAdById(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeAd(@RequestParam(required = true) Integer id) {
        adsService.removeAd(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @PatchMapping("{id}")
    public Ad updateAds(@RequestParam(required = true) Integer id,
                        @RequestBody CreateOrUpdateAd createOrUpdateAd) {
        return adsService.updateAds(id, createOrUpdateAd);
    }

    @GetMapping("/me")
    public Ads getAdsMe() {
        return adsService.getAdsMe();
    }

    @PatchMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImage(@RequestParam(required = true) Integer id,
                                        @RequestBody MultipartFile image) {
        return adsService.updateImage(id, image);
    }
}

