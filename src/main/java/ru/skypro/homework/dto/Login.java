package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Size;

@Data
@Schema(description = "ДТО для аутентификации пользователя")
public class Login {

    @NonNull
    @Size(min = 4, max = 32)
    private String username;
    @NonNull
    @Size(min = 8, max = 16)
    private String password;
}
