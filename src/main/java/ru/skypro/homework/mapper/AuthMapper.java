package ru.skypro.homework.mapper;

import ru.skypro.homework.dto.accept.Register;
import ru.skypro.homework.entity.UserEntity;

public interface AuthMapper {

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
