package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.accept.CreateOrUpdateAd;
import ru.skypro.homework.dto.give.Ad;
import ru.skypro.homework.dto.give.Ads;
import ru.skypro.homework.dto.give.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.skypro.NewTypeTesting.TestHelper.*;
import static ru.skypro.homework.mapper.AdMapper.*;

@SpringBootTest
public class AdMapperTest {

    @Test
    void shouldCorrectlyMapCreatedOrUpdatedAdToAdEntity() {
        CreateOrUpdateAd createOrUpdateAd = getCreateOrUpdateAd();
        AdEntity adEntity = getFullAdEntity();

        AdEntity mappedAdEntity = toAdEntity(adEntity, createOrUpdateAd);

        assertThat(mappedAdEntity).isNotNull();
        assertThat(mappedAdEntity.getTitle()).isEqualTo(createOrUpdateAd.getTitle());
        assertThat(mappedAdEntity.getPrice()).isEqualTo(createOrUpdateAd.getPrice());
        assertThat(mappedAdEntity.getDescription()).isEqualTo(createOrUpdateAd.getDescription());
    }

    @Test
    void shouldCorrectlyMapCreatedOrUpdatedAdToAdEntityWhenCreate() {
        CreateOrUpdateAd createOrUpdateAd = getCreateOrUpdateAd();
        UserEntity userEntity = getFullUserEntity();

        AdEntity mappedAdEntity = toCreatedAdEntity(userEntity, createOrUpdateAd);

        assertThat(mappedAdEntity).isNotNull();
        assertThat(mappedAdEntity.getTitle()).isEqualTo(createOrUpdateAd.getTitle());
        assertThat(mappedAdEntity.getPrice()).isEqualTo(createOrUpdateAd.getPrice());
        assertThat(mappedAdEntity.getDescription()).isEqualTo(createOrUpdateAd.getDescription());
    }

    @Test
    void shouldCorrectlyMapToAd() {
        AdEntity adEntity = getFullAdEntity();

        Ad ad = toAd(adEntity);

        assertThat(ad).isNotNull();
        assertThat(ad.getAuthor()).isEqualTo(adEntity.getUser().getId());
        assertThat(ad.getImage()).isEqualTo(adEntity.getImage());
        assertThat(ad.getPk()).isEqualTo(adEntity.getPk());
        assertThat(ad.getTitle()).isEqualTo(adEntity.getTitle());
        assertThat(ad.getPrice()).isEqualTo(adEntity.getPrice());
    }

    @Test
    void shouldCorrectlyMapToExtendedAd() {
        AdEntity adEntity = getFullAdEntity();

        ExtendedAd extendedAd = toExtendedAd(adEntity);

        assertThat(extendedAd).isNotNull();
        assertThat(extendedAd.getPk()).isEqualTo(adEntity.getPk());
        assertThat(extendedAd.getAuthorFirstName()).isEqualTo(adEntity.getUser().getFirstName());
        assertThat(extendedAd.getAuthorLastName()).isEqualTo(adEntity.getUser().getLastName());
        assertThat(extendedAd.getDescription()).isEqualTo(adEntity.getDescription());
        assertThat(extendedAd.getEmail()).isEqualTo(adEntity.getUser().getUsername());
        assertThat(extendedAd.getImage()).isEqualTo(adEntity.getImage());
        assertThat(extendedAd.getPrice()).isEqualTo(adEntity.getPrice());
        assertThat(extendedAd.getTitle()).isEqualTo(adEntity.getTitle());
    }

    @Test
    void shouldCorrectlyMapAdEntityToAd() {
        AdEntity adEntity = getFullAdEntity();
        CreateOrUpdateAd updateAd = getCreateOrUpdateAd();

        Ad ad = toAd(updateAd, adEntity);

        assertThat(ad).isNotNull();
        assertThat(ad.getAuthor()).isEqualTo(adEntity.getUser().getId());
        assertThat(ad.getImage()).isEqualTo(adEntity.getImage());
        assertThat(ad.getPk()).isEqualTo(adEntity.getPk());
        assertThat(ad.getPrice()).isEqualTo(updateAd.getPrice());
        assertThat(ad.getTitle()).isEqualTo(updateAd.getTitle());
    }

    @Test
    void shouldCorrectlyMapToAds() {
        List<AdEntity> adEntityList = List.of(getFullAdEntity());
        Ads ads = toAds(adEntityList);

        assertThat(ads).isNotNull();

        Ad mappedAd = ads.getResults().get(0);
        AdEntity testAd = adEntityList.get(0);

        assertThat(mappedAd.getAuthor()).isEqualTo(testAd.getUser().getId());
        assertThat(mappedAd.getImage()).isEqualTo(testAd.getImage());
        assertThat(mappedAd.getPk()).isEqualTo(testAd.getPk());
        assertThat(mappedAd.getPrice()).isEqualTo(testAd.getPrice());
        assertThat(mappedAd.getTitle()).isEqualTo(testAd.getTitle());

    }

}
