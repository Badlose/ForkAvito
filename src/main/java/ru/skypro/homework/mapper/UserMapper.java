package ru.skypro.homework.mapper;

import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.give.User;
import ru.skypro.homework.entity.UserEntity;

public interface UserMapper {

    static User toUser(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .email(entity.getUsername())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .phone(entity.getPhone())
                .role(entity.getRole())
                .image(entity.getImage())
                .build();
    }

    static UserEntity toUserEntity(UserEntity userEntity, UpdateUser updateUser) {
        return UserEntity.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .firstName(updateUser.getFirstName())
                .lastName(updateUser.getLastName())
                .phone(updateUser.getPhone())
                .role(userEntity.getRole())
                .image(userEntity.getImage())
                .ads(userEntity.getAds())
                .comments(userEntity.getComments())
                .build();
    }

}
