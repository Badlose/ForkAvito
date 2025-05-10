package ru.skypro.homework.mapper;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.skypro.homework.dto.accept.CreateOrUpdateAd;
import ru.skypro.homework.dto.give.Ad;
import ru.skypro.homework.dto.give.Ads;
import ru.skypro.homework.dto.give.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public interface AdMapper {

    static AdEntity toCreatedAdEntity(UserEntity userEntity, CreateOrUpdateAd ad) {
        return AdEntity.builder()
                .image(userEntity.getImage())
                .price(ad.getPrice())
                .title(ad.getTitle())
                .description(ad.getDescription())
                .user(userEntity)
                .build();
    }

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
    }

    static Ad toAd(CreateOrUpdateAd updateAd, AdEntity entity) {
        return Ad.builder()
                .author(entity.getUser().getId())
                .image(entity.getImage())
                .pk(entity.getPk())
                .price(updateAd.getPrice())
                .title(updateAd.getTitle())
                .build();
    }

    static ExtendedAd toExtendedAd(AdEntity adEntity) {
        return ExtendedAd.builder()
                .pk(adEntity.getPk())
                .authorFirstName(adEntity.getUser().getFirstName())
                .authorLastName(adEntity.getUser().getLastName())
                .description(adEntity.getDescription())
                .email(adEntity.getUser().getUsername())
                .image(adEntity.getImage())
                .phone(adEntity.getUser().getPhone())
                .price(adEntity.getPrice())
                .title(adEntity.getTitle())
                .build();
    }

    static Ad toAd(AdEntity entity) {
        return Ad.builder()
                .author(entity.getUser().getId())
                .image(entity.getImage())
                .pk(entity.getPk())
                .price(entity.getPrice())
                .title(entity.getTitle())
                .build();
    }

    static Ads toAds(List<AdEntity> ads) {
        List<Ad> adList = new ArrayList<>();
        for (AdEntity adEntity : ads) {
            adList.add(toAd(adEntity));
        }
        return new Ads(adList.size(), adList);
    }

}
