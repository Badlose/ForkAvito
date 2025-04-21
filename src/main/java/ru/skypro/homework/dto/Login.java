package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

@Data
@Schema(description = "ДТО для аутентификации пользователя")
public class Login {

    @NonNull
    private String username;
    @NonNull
    private String password;
}
