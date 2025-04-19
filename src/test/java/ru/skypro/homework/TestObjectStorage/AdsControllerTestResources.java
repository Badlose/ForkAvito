package ru.skypro.homework.TestObjectStorage;

import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

import java.util.ArrayList;
import java.util.List;

public abstract class AdsControllerTestResources {

    protected Ad getTestAd() {
        Ad ad = new Ad();
        ad.setAuthor(1);
        ad.setImage("ad image");
        ad.setPk(1);
        ad.setPrice(1_000);
        ad.setTitle("ad title");
        return ad;
    }

    protected Ads getTestAds() {
        Ad ad = new Ad();
        ad.setAuthor(1);
        ad.setImage("ad for ads image");
        ad.setPk(1);
        ad.setPrice(1_000);
        ad.setTitle("ad for ads title");

        List<Ad> adList = new ArrayList<>(List.of(ad));

        Ads ads = new Ads();
        ads.setCount(1);
        ads.setResult(adList);

        return ads;
    }

    protected CreateOrUpdateAd getTestCreatedAd() {
        CreateOrUpdateAd createdAd = new CreateOrUpdateAd();
        createdAd.setTitle("created ad title");
        createdAd.setPrice(2_000);
        createdAd.setDescription("created ad description");
        return createdAd;
    }

    protected MultipartFile getTestMultipartFile() {
        return Mockito.mock(MultipartFile.class);
    }

    protected ExtendedAd getTestExtendedAd() {
        ExtendedAd extendedAd = new ExtendedAd();
        extendedAd.setPk(1);
        extendedAd.setAuthorFirstName("extended ad author first name");
        extendedAd.setAuthorLastName("extended ad author last name");
        extendedAd.setDescription("extended ad description");
        extendedAd.setEmail("extended ad email");
        extendedAd.setImage("extended ad image");
        extendedAd.setPhone("extended ad phone");
        extendedAd.setPrice(12_000);
        extendedAd.setTitle("extended ad title");
        return extendedAd;
    }

}