//package ru.skypro.homework.mapper;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.skypro.homework.dto.accept.CreateOrUpdateAd;
//import ru.skypro.homework.dto.give.Ad;
//import ru.skypro.homework.dto.give.Ads;
//import ru.skypro.homework.dto.give.ExtendedAd;
//import ru.skypro.homework.entity.AdEntity;
//import ru.skypro.homework.entity.UserEntity;
//import ru.skypro.homework.mapper.UserMapperTestResourceStorage.AdMapperTestResources;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Slf4j
//public class AdMapperTest extends AdMapperTestResources {
//
//    @Autowired
//    private AdMapper mapper;
//
//    @Test
//    void shouldCorrectlyMapCreatedOrUpdatedAdToAdEntity() {
//
//        CreateOrUpdateAd createOrUpdateAd = getTestCreatedAd();
//
//        AdEntity adEntity = getTestAdEntity();
//
//        mapper.toAdEntity(createOrUpdateAd, adEntity);
//
//        assertThat(adEntity).isNotNull();
//        assertThat(adEntity.getTitle()).isEqualTo(createOrUpdateAd.getTitle());
//        assertThat(adEntity.getPrice()).isEqualTo(createOrUpdateAd.getPrice());
//        assertThat(adEntity.getDescription()).isEqualTo(createOrUpdateAd.getDescription());
//        log.info("shouldCorrectlyMapCreatedOrUpdatedAdToAdEntity AdEntity " + adEntity);
//    }
//
//    @Test
//    void shouldCorrectlyMapToAd() {
//
//        CreateOrUpdateAd createOrUpdateAd = getTestCreatedAd();
//
//        AdEntity adEntity = getTestAdEntity();
//
//        Ad ad = getTestAd();
//
//        mapper.toAd(createOrUpdateAd, adEntity, ad);
//
//        assertThat(ad).isNotNull();
//        assertThat(ad.getAuthor()).isEqualTo(adEntity.getAuthor());
//        assertThat(ad.getImage()).isEqualTo(adEntity.getImage());
//        assertThat(ad.getPk()).isEqualTo(adEntity.getPk());
//        assertThat(ad.getTitle()).isEqualTo(createOrUpdateAd.getTitle());
//        assertThat(ad.getPrice()).isEqualTo(createOrUpdateAd.getPrice());
//        log.info("shouldCorrectlyMapToAd Ad " + ad);
//    }
//
//    @Test
//    void shouldCorrectlyMapToExtendedAd() {
//        UserEntity userEntity = getTestUserEntity();
//        AdEntity adEntity = getTestAdEntity();
//        ExtendedAd extendedAd = getTestExtendedAd();
//
//        mapper.toExtendedAd(userEntity, adEntity, extendedAd);
//
//        assertThat(extendedAd).isNotNull();
//        assertThat(extendedAd.getPk()).isEqualTo(adEntity.getPk());
//        assertThat(extendedAd.getAuthorFirstName()).isEqualTo(userEntity.getFirstName());
//        assertThat(extendedAd.getAuthorLastName()).isEqualTo(userEntity.getLastName());
//        assertThat(extendedAd.getDescription()).isEqualTo(adEntity.getDescription());
//        assertThat(extendedAd.getEmail()).isEqualTo(userEntity.getUsername());
//        assertThat(extendedAd.getImage()).isEqualTo(adEntity.getImage());
//        assertThat(extendedAd.getPrice()).isEqualTo(adEntity.getPrice());
//        assertThat(extendedAd.getTitle()).isEqualTo(adEntity.getTitle());
//
//        log.info("shouldCorrectlyMapToExtendedAd extendedAd " + extendedAd);
//    }
//
//    @Test
//    void shouldCorrectlyMapAdEntityToAd() {
//        AdEntity adEntity = getTestAdEntity();
//        Ad ad = getTestAd();
//
//        mapper.toAd(adEntity, ad);
//
//        assertThat(ad).isNotNull();
//
//        assertThat(ad.getAuthor()).isEqualTo(adEntity.getAuthor());
//        assertThat(ad.getImage()).isEqualTo(adEntity.getImage());
//        assertThat(ad.getPk()).isEqualTo(adEntity.getPk());
//        assertThat(ad.getPrice()).isEqualTo(adEntity.getPrice());
//        assertThat(ad.getTitle()).isEqualTo(adEntity.getTitle());
//
//        log.info("shouldCorrectlyMapAdEntityToAd Ad " + ad);
//    }
//
//    @Test
//    void shouldCorrectlyMapToListAd() {
//        List<AdEntity> adEntityList = List.of(getTestAdEntity());
//
//        List<Ad> adList = mapper.toAdList(adEntityList);
//
//        assertThat(adList).isNotNull();
//
//        Ad mappedAd = adList.get(0);
//        AdEntity testAd = adEntityList.get(0);
//
//        assertThat(mappedAd.getAuthor()).isEqualTo(testAd.getAuthor());
//        assertThat(mappedAd.getImage()).isEqualTo(testAd.getImage());
//        assertThat(mappedAd.getPk()).isEqualTo(testAd.getPk());
//        assertThat(mappedAd.getPrice()).isEqualTo(testAd.getPrice());
//        assertThat(mappedAd.getTitle()).isEqualTo(testAd.getTitle());
//
//        log.info("shouldCorrectlyMapToListAd Ad " + mappedAd);
//    }
//
//    @Test
//    void shouldCorrectlyMapToAds() {
//        Integer count = 1;
//        List<AdEntity> adEntityList = List.of(getTestAdEntity());
//
//        Ads ads = mapper.toAds(count, adEntityList);
//
//        assertThat(ads).isNotNull();
//
//        Ad mappedAd = ads.getResult().get(0);
//        AdEntity testAd = adEntityList.get(0);
//
//        assertThat(mappedAd.getAuthor()).isEqualTo(testAd.getAuthor());
//        assertThat(mappedAd.getImage()).isEqualTo(testAd.getImage());
//        assertThat(mappedAd.getPk()).isEqualTo(testAd.getPk());
//        assertThat(mappedAd.getPrice()).isEqualTo(testAd.getPrice());
//        assertThat(mappedAd.getTitle()).isEqualTo(testAd.getTitle());
//
//        log.info("shouldCorrectlyMapToAds Ad " + mappedAd);
//    }
//
//}
