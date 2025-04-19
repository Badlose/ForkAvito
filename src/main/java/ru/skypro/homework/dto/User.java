package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "ДТО для получения информации об авторизованном пользователе")
public class User {

    private Integer id;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String image;
}
