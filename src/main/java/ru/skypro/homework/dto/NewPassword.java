package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "ДТО для обновления пароля")
public class NewPassword {

    @Schema(description = "Текущий пароль")
    String currentPassword;
    @Schema(description = "Новый пароль")
    String newPassword;
}
