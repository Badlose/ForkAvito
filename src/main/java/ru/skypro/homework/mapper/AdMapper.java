package ru.skypro.homework.mapper;

import ru.skypro.homework.dto.accept.CreateOrUpdateAd;
import ru.skypro.homework.dto.give.Ad;
import ru.skypro.homework.dto.give.Ads;
import ru.skypro.homework.dto.give.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

//@Mapper(componentModel = "spring")
public interface AdMapper {

//    @Mapping(target = "pk", ignore = true)
//    @Mapping(target = "image", ignore = true)
//    @Mapping(target = "user", ignore = true)
//    @Mapping(target = "comments", ignore = true)
//    void toAdEntity(UserEntity user, CreateOrUpdateAd ad, @MappingTarget AdEntity entity);

static AdEntity createAdEntity(UserEntity userEntity, CreateOrUpdateAd ad) {
    return AdEntity.builder()
            .image(userEntity.getImage())
            .price(ad.getPrice())
            .title(ad.getTitle())
            .description(ad.getDescription())
            .user(userEntity)
            .build();
};
    static AdEntity toAdEntity(AdEntity adEntity, CreateOrUpdateAd ad) {
        return AdEntity.builder()
                .pk(adEntity.getPk())
                .image(adEntity.getImage())
                .price(ad.getPrice())
                .title(ad.getTitle())
                .description(ad.getDescription())
                .user(adEntity.getUser())
                .comments(adEntity.getComments())
                .build();
    };


//    @Mapping(source = "updateAd.price", target = "price")
//    @Mapping(source = "updateAd.title", target = "title")
//    void toAd(CreateOrUpdateAd updateAd, AdEntity entity, @MappingTarget Ad ad);

    static Ad toAd(CreateOrUpdateAd updateAd, AdEntity entity) {
        return Ad.builder()
                .author(entity.getUser().getId())
                .image(entity.getImage())
                .pk(entity.getPk())
                .price(updateAd.getPrice())
                .title(updateAd.getTitle())
                .build();
    }

//    @Mapping(source = "adEntity.image", target = "image")
//    @Mapping(source = "userEntity.firstName", target = "authorFirstName")
//    @Mapping(source = "userEntity.lastName", target = "authorLastName")
//    @Mapping(source = "userEntity.username", target = "email")
//    void toExtendedAd(UserEntity userEntity, AdEntity adEntity, @MappingTarget ExtendedAd extendedAd);

    static ExtendedAd toExtendedAd(AdEntity adEntity) {
        return ExtendedAd.builder()
                .pk(adEntity.getPk())
                .authorFirstName(adEntity.getUser().getFirstName())
                .authorLastName(adEntity.getUser().getFirstName())
                .description(adEntity.getDescription())
                .email(adEntity.getUser().getUsername())
                .image(adEntity.getImage())
                .phone(adEntity.getUser().getPhone())
                .price(adEntity.getPrice())
                .title(adEntity.getTitle())
                .build();
    }
//    void toAd(AdEntity entity, @MappingTarget Ad ad);

    static Ad toAd(AdEntity entity) {
        return Ad.builder()
                .author(entity.getUser().getId())
                .image(entity.getImage())
                .pk(entity.getPk())
                .price(entity.getPrice())
                .title(entity.getTitle())
                .build();
    }

//    List<Ad> toAdList(List<AdEntity> entityList);
//
//    default Ads toAds(Integer count, List<AdEntity> ads) {
//        List<Ad> adsDTO = toAdList(ads);
//        return new Ads(count, adsDTO);
//    }

    static Ads toAds(List<AdEntity> ads) {

        List<Ad> adList = new ArrayList<>();

        for (AdEntity adEntity : ads) {
            adList.add(toAd(adEntity));
        }

        return new Ads(adList.size(), adList);
    }

}
