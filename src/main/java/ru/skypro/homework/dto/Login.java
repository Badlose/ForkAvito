package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "ДТО для аутентификации пользователя")
public class Login {

    private String username;
    private String password;
}
