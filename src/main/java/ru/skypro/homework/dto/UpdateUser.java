package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Schema(description = "ДТО для обновления информации обавторизованном пользователе")
public class UpdateUser {

    @NonNull
    @Size(min = 3, max = 10)
    private String firstName;
    @NonNull
    @Size(min = 3, max = 10)
    private String lastName;
    @NonNull
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}", message = "Неверный формат номера телефона")
    private String phone;
}
