package ru.skypro.homework.mapper.UserMapperTestResourceStorage;

import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.accept.CreateOrUpdateAd;
import ru.skypro.homework.dto.give.Ad;
import ru.skypro.homework.dto.give.Ads;
import ru.skypro.homework.dto.give.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public abstract class AdMapperTestResources {

    protected CreateOrUpdateAd getTestCreatedAd() {
        String adTitle = "update title";
        Integer adPrice = 100;
        String adDescription = "created ad description";
        return new CreateOrUpdateAd(adTitle, adPrice, adDescription);
    }

    protected AdEntity getTestAdEntity() {
        Integer author = 1;
        String image = "image";
        Integer pk = 1;
        Integer price = 1_000;
        String title = "title";
        String description = "description";
        UserEntity user = mock(UserEntity.class);
        List<CommentEntity> comments = new ArrayList<>(List.of(mock(CommentEntity.class)));
        AdEntity entity = new AdEntity(author, image, pk, price, title, description, user, comments);
        return entity;
    }

    protected Ad getTestAd() {
        Integer author = 2;
        String image = "ad image";
        Integer pk = 2;
        Integer price = 2_000;
        String title = "ad title";
        return new Ad(author, image, pk, price, title);
    }

    protected UserEntity getTestUserEntity() {
        Integer id = 1;
        String username = "username";
        String password = "password";
        String firstName = "firstName";
        String lastName = "lastName";
        String phone = "phone";
        Role role = Role.USER;
        String image = "image";
        List<AdEntity> adEntities = new ArrayList<>(List.of(mock(AdEntity.class)));
        List<CommentEntity> commentEntityList = new ArrayList<>(List.of(mock(CommentEntity.class)));

        UserEntity userEntity = new UserEntity(
                id, username, password, firstName, lastName, phone, role, image,
                adEntities, commentEntityList);

        return userEntity;
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

    protected Ads getTestAds() {
        Integer id = 3;
        String adImage = "ads image";
        Integer adAuthor = 3;
        Integer adPrice = 3_000;
        String adTitle = "ads title";
        Ad ad = new Ad(id, adImage, adAuthor, adPrice, adTitle);

        List<Ad> adList = new ArrayList<>(List.of(ad));
        Integer adsCount = 1;

        return new Ads(adsCount, adList);
    }

}
