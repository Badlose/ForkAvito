package ru.skypro.homework.controller.TestObjectsForControllerTestStorage;

import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.give.Ad;
import ru.skypro.homework.dto.give.Ads;
import ru.skypro.homework.dto.accept.CreateOrUpdateAd;
import ru.skypro.homework.dto.give.ExtendedAd;

import java.util.ArrayList;
import java.util.List;

public abstract class AdsControllerTestResources {

    protected Ad getTestAd() {
        Integer id = 1;
        String adImage = "ad image";
        Integer adAuthor = 1;
        Integer adPrice = 1_000;
        String adTitle = "ad title";
        return new Ad(id, adImage, adAuthor, adPrice, adTitle);
    }

    protected Ads getTestAds() {
        Integer id = 1;
        String adImage = "ad image";
        Integer adAuthor = 1;
        Integer adPrice = 1_000;
        String adTitle = "ad title";
        Ad ad = new Ad(id, adImage, adAuthor, adPrice, adTitle);

        List<Ad> adList = new ArrayList<>(List.of(ad));
        Integer adsCount = 1;

        return new Ads(adsCount, adList);
    }

    protected CreateOrUpdateAd getTestCreatedAd() {
        String adTitle = "ad title";
        Integer adPrice = 1_000;
        String adDescription = "created ad description";
        return new CreateOrUpdateAd(adTitle, adPrice, adDescription);
    }

    protected MultipartFile getTestMultipartFile() {
        return Mockito.mock(MultipartFile.class);
    }

    protected ExtendedAd getTestExtendedAd() {
        Integer pk = 1;
        String adAuthorFirstName = "extended ad author first name";
        String adAuthorLastName = "extended ad author last name";
        String adDescription = "extended ad description";
        String adEmail = "extended ad email";
        String adImage = "extended ad image";
        String adPhone = "extended ad phone";
        Integer adPrice = 1_000;
        String adTitle = "extended ad title";
        return new ExtendedAd(pk, adAuthorFirstName, adAuthorLastName,
                adDescription, adEmail, adImage, adPhone, adPrice, adTitle);
    }

}