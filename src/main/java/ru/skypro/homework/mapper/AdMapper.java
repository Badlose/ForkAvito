package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.skypro.homework.dto.accept.CreateOrUpdateAd;
import ru.skypro.homework.dto.give.Ad;
import ru.skypro.homework.dto.give.Ads;
import ru.skypro.homework.dto.give.ExtendedAd;
import ru.skypro.homework.dto.give.User;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AdMapper {

    @Mapping(target = "pk", ignore = true)
    @Mapping(source = "user.id", target = "author")
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "comments", ignore = true)
    void toAdEntity(UserEntity user, CreateOrUpdateAd ad, @MappingTarget AdEntity entity);

    @Mapping(source = "updateAd.price", target = "price")
    @Mapping(source = "updateAd.title", target = "title")
    void toAd(CreateOrUpdateAd updateAd, AdEntity entity, @MappingTarget Ad ad);

    @Mapping(source = "adEntity.image", target = "image")
    @Mapping(source = "userEntity.firstName", target = "authorFirstName")
    @Mapping(source = "userEntity.lastName", target = "authorLastName")
    @Mapping(source = "userEntity.username", target = "email")
    void toExtendedAd(UserEntity userEntity, AdEntity adEntity, @MappingTarget ExtendedAd extendedAd);

    void toAd(AdEntity entity, @MappingTarget Ad ad);

    List<Ad> toAdList(List<AdEntity> entityList);

    default Ads toAds(Integer count, List<AdEntity> ads) {
        List<Ad> adsDTO = toAdList(ads);
        return new Ads(count, adsDTO);
    }

    static Ads staticToAds(Integer count, List<AdEntity> ads) {

        List<Ad> adList = ads.stream()
                .map(adEntity -> {
                    Ad ad = new Ad();
                    ad.setPk(adEntity.getPk());
                    ad.setAuthor(adEntity.getAuthor());
                    ad.setImage(adEntity.getImage());
                    ad.setPrice(adEntity.getPrice());
                    ad.setTitle(adEntity.getTitle());
                    return ad;
                }).collect(Collectors.toList());

        return new Ads(count, adList);
    }

}
