package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdsController {

    private AdsService adsService;

    @GetMapping
    public ResponseEntity<?> getAllAds(@RequestBody AllAdsDTO allAdsDTO) {

    }

    @PostMapping
    public ResponseEntity<?> addAd(@RequestBody adToAddDTO adToAddDTO) {
        if (adsService.addNewAd(adToAddDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("{id}")
    public ExtendedAd getAds(@PathVariable Long adId) {
        return adsService.getAdById(adId);
    }

    @DeleteMapping("{id}")

