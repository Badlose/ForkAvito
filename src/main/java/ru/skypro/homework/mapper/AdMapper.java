package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.skypro.homework.dto.accept.CreateOrUpdateAd;
import ru.skypro.homework.dto.give.Ad;
import ru.skypro.homework.dto.give.Ads;
import ru.skypro.homework.dto.give.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdMapper {

    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "comments", ignore = true)
    void toAdEntity(CreateOrUpdateAd ad, @MappingTarget AdEntity entity);

    @Mapping(source = "entity.price", target = "price", ignore = true)
    @Mapping(source = "entity.title", target = "title", ignore = true)
    void toAd(CreateOrUpdateAd updateAd, AdEntity entity, @MappingTarget Ad ad);

    @Mapping(source = "entity.image", target = "image", ignore = true)
    @Mapping(source = "entity.firstName", target = "authorFirstName")
    @Mapping(source = "entity.lastName", target = "authorLastName")
    @Mapping(source = "entity.username", target = "email")
    void toExtendedAd(UserEntity entity, AdEntity ad, @MappingTarget ExtendedAd extendedAd);

    void toAd(AdEntity entity, @MappingTarget Ad ad);

    List<Ad> toAdList(List<AdEntity> entityList);

    default Ads toAds(Integer count, List<AdEntity> ads) {
        List<Ad> adsDTO = toAdList(ads);
        return new Ads(count, adsDTO);
    }
}
