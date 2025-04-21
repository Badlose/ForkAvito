package ru.skypro.homework.dto.give;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;
import ru.skypro.homework.dto.Role;

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
