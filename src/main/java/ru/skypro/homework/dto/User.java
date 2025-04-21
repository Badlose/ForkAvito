package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

@Data
@Schema(description = "ДТО для получения информации об авторизованном пользователе")
public class User {

    @NonNull
    private Integer id;
    @NonNull
    private String email;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String phone;
    @NonNull
    private Role role;
    @NonNull
    private String image;
}
