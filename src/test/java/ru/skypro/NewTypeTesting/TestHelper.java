package ru.skypro.NewTypeTesting;

import lombok.RequiredArgsConstructor;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.accept.*;
import ru.skypro.homework.dto.give.*;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.CustomUserDetails;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;

@RequiredArgsConstructor
public class TestHelper {

    private static final PodamFactory factory = new PodamFactoryImpl();
    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final CommentRepository commentRepository;


    public static UserEntity.UserEntityBuilder createUserEntityBuilder() {
        return UserEntity.builder()
                .id(1)
                .username("test user")
                .password("test user")
                .firstName("test user")
                .lastName("test user")
                .phone("+7(981)111-11-11")
                .role(Role.USER);
    }

    public static UserEntity createUserEntity() {
        return createUserEntityBuilder().build();
    }

    public UserEntity saveUserEntity(UserEntity entity) {
        return userRepository.save(entity);
    }

    public void deleteUserEntity(Integer id) {
        userRepository.deleteById(id);
    }

    public static CreateOrUpdateAd getCreateOrUpdateAd() {
        return factory.manufacturePojo(CreateOrUpdateAd.class);
    }

    public static CreateOrUpdateComment getCreateOrUpdateComment() {
        return factory.manufacturePojo(CreateOrUpdateComment.class);
    }

    public static Login getLogin() {
        return factory.manufacturePojo(Login.class);
    }

    public static NewPassword getNewPassword() {
        return factory.manufacturePojo(NewPassword.class);
    }

    public static Register getRegister() {
        return factory.manufacturePojo(Register.class);
    }

    public static Ad getAd() {
        return factory.manufacturePojo(Ad.class);
    }

    public static Ads getAds() {
        return factory.manufacturePojo(Ads.class);
    }

    public static Comment getComment() {
        return factory.manufacturePojo(Comment.class);
    }

    public static Comments getComments() {
        return factory.manufacturePojo(Comments.class);
    }

    public static ExtendedAd getExtendedAd() {
        return factory.manufacturePojo(ExtendedAd.class);
    }

    public static User getUser() {
        return factory.manufacturePojo(User.class);
    }

    public static Role getRole() {
        return factory.manufacturePojo(Role.class);
    }

    public static UpdateUser getUpdateUser() {
        return factory.manufacturePojo(UpdateUser.class);
    }

    public static AdEntity getAdEntity() {
        return factory.manufacturePojo(AdEntity.class);
    }

    public static CommentEntity getCommentEntity() {
        return factory.manufacturePojo(CommentEntity.class);
    }

    public static UserEntity getUserEntity() {
        return factory.manufacturePojo(UserEntity.class);
    }

    public static UserEntity getFullUserEntity() {
        UserEntity userEntity = getUserEntity();
        AdEntity adEntity = getAdEntity();
        adEntity.setComments(List.of(getCommentEntity()));
        userEntity.setAds(List.of(adEntity));
        return userEntity;
    }

    public static AdEntity getFullAdEntity() {
        return factory.manufacturePojoWithFullData(AdEntity.class);
    }

    public static CommentEntity getFullCommentEntity() {
        CommentEntity commentEntity = getCommentEntity();
        commentEntity.setUser((getUserEntity()));
        commentEntity.setAd(getAdEntity());
        return commentEntity;
    }

    public static CustomUserDetails getCustomUserDetails() {
        return factory.manufacturePojo(CustomUserDetails.class);
    }


    public static void main(String[] args) {
        System.out.println(getFullCommentEntity());
        System.out.println(getFullAdEntity());
        System.out.println(getFullUserEntity());
    }

}
