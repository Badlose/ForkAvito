package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

@Data
@Schema(description = "ДТО для обновления пароля")
public class NewPassword {

    @Schema(description = "Текущий пароль")
    @NonNull
    private String currentPassword;
    @NonNull
    @Schema(description = "Новый пароль")
    private String newPassword;
}
