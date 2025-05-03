package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.accept.Register;
import ru.skypro.homework.dto.give.User;
import ru.skypro.homework.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    //    @Mapping(source = "username", target = "email")
//    User toUser(UserEntity entity);
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

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "username", ignore = true)
//    @Mapping(target = "password", ignore = true)
//    @Mapping(target = "role", ignore = true)
//    @Mapping(target = "image", ignore = true)
//    @Mapping(target = "ads", ignore = true)
//    @Mapping(target = "comments", ignore = true)
//    UserEntity toUserEntity(UpdateUser updateUser);

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
