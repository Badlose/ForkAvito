package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.skypro.homework.dto.accept.NewPassword;
import ru.skypro.homework.dto.accept.Register;
import ru.skypro.homework.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface AuthMapper {

//    @Mapping(source = "newPassword", target = "password")
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "username", ignore = true)
//    @Mapping(target = "firstName", ignore = true)
//    @Mapping(target = "lastName", ignore = true)
//    @Mapping(target = "phone", ignore = true)
//    @Mapping(target = "role", ignore = true)
//    @Mapping(target = "image", ignore = true)
//    @Mapping(target = "ads", ignore = true)
//    @Mapping(target = "comments", ignore = true)
//    UserEntity toUserEntity(NewPassword password);

//    static UserEntity setNewPassword(NewPassword password) {
//        return UserEntity.builder()
//                .password(password.getNewPassword())
//                .build();
//    }

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "image", ignore = true)
//    @Mapping(target = "ads", ignore = true)
//    @Mapping(target = "comments", ignore = true)
//    UserEntity toUserEntity(Register register);

    static UserEntity createNewUser(Register register) {
        return UserEntity.builder()
                .username(register.getUsername())
                .password(register.getPassword())
                .firstName(register.getFirstName())
                .lastName(register.getLastName())
                .phone(register.getPhone())
                .role(register.getRole())
                .build();
    }
}
