package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Schema(description = "ДТО для обновления информации обавторизованном пользователе")
public class UpdateUser {

    @NotBlank(message = "Firstname is required")
    @Size(min = 3, max = 10, message = "Firstname must be between 3 and 10 characters")
    private String firstName;
    @NotBlank(message = "Lastname is required")
    @Size(min = 3, max = 10, message = "Lastname must be between 3 and 10 characters")
    private String lastName;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}",
            message = "Неверный формат номера телефона")
    private String phone;
}
