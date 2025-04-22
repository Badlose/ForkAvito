package ru.skypro.homework.mapper.UserMapperTestResourceStorage;

import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.accept.NewPassword;
import ru.skypro.homework.dto.accept.Register;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class AuthMapperTestResources {

    protected UserEntity getTestUserEntity() {
        Integer id = 1;
        String username = "username";
        String password = "password";
        String firstName = "firstName";
        String lastName = "lastName";
        String phone = "phone";
        Role role = Role.USER;
        String image = "image";
        List<AdEntity> adEntities = new ArrayList<>();
        List<CommentEntity> commentEntityList = new ArrayList<>();

        UserEntity userEntity = new UserEntity(
                id, username, password, firstName, lastName, phone, role, image,
                adEntities, commentEntityList);

        return userEntity;
    }

    protected NewPassword getTestNewPassword() {
        String currentPassword = "current";
        String newPassword = "newPassword";
        return new NewPassword(currentPassword, newPassword);
    }

    protected Register getTestRegister() {
        String username = "username";
        String userPassword = "password";
        String userFirstName = "user first name";
        String userLastName = "user last name";
        String userPhone = "user phone";
        Role userRole = Role.USER;
        return new Register(username, userPassword, userFirstName, userLastName, userPhone, userRole);
    }

}
