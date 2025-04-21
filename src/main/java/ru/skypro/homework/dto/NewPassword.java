package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Size;

@Data
@Schema(description = "ДТО для обновления пароля")
public class NewPassword {

    @Schema(description = "Текущий пароль")
    @NonNull
    @Size(min = 8, max = 16)
    private String currentPassword;
    @NonNull
    @Schema(description = "Новый пароль")
    @Size(min = 8, max = 16)
    private String newPassword;
}
