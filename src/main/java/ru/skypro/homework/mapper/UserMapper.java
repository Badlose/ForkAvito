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

    @Mapping(source = "username", target = "email")
    void toUser(UserEntity entity, @MappingTarget User user);

    void toUpdatedUser(UserEntity entity, @MappingTarget UpdateUser updateUser);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "ads", ignore = true)
    @Mapping(target = "comments", ignore = true)
    void toUserEntity(UpdateUser updateUser, @MappingTarget UserEntity entity);

}
