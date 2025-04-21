package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Size;

@Data
@Schema(description = "ДТО для регистрации нового пользователя")
public class Register {

    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    @Size(min = 3, max = 10)
    private String firstName;
    @NonNull
    @Size(min = 3, max = 10)
    private String lastName;
    @NonNull
    private String phone;
    @NonNull
    private Role role;
}
