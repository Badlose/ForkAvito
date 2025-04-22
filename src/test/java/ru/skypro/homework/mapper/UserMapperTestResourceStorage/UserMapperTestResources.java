package ru.skypro.homework.mapper.UserMapperTestResourceStorage;

import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.give.User;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class UserMapperTestResources {

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

    protected User getTestUser() {
        Integer id = 1;
        String userEmail = "user email";
        String userFirstName = "user first name";
        String userLastName = "user last name";
        String userPhone = "user phone";
        Role role = Role.USER;
        String userImage = "user image";
        return new User(id, userEmail, userFirstName, userLastName, userPhone, role, userImage);
    }

    protected UpdateUser getTestUpdateUser() {
        String userFirstName = "mapperName";
        String userLastName = "mapperLast";
        String userPhone = "mapperphone";
        return new UpdateUser(userFirstName, userLastName, userPhone);
    }

}
