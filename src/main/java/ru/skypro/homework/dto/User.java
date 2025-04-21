package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Schema(description = "ДТО для получения информации об авторизованном пользователе")
public class User {

    @NonNull
    private Integer id;
    @NonNull
    @Size(min = 3, max = 10)
    private String firstName;
    @NonNull
    @Size(min = 3, max = 10)
    private String lastName;
    @NonNull
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}", message = "Неверный формат номера телефона")
    private String phone;
    @NonNull
    private Role role;
    @NonNull
    private String image;
}
