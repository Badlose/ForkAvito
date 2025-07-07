package ru.skypro.homework.dto.accept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Schema(description = "ДТО для обновления пароля")
public class NewPassword {

    @Schema(description = "Текущий пароль")
    @NotBlank(message = "Current password is required")
    @Size(min = 8, max = 16, message = "Current password must be between 8 and 16 characters")
    private String currentPassword;

    @Schema(description = "Новый пароль")
    @NotBlank(message = "New password is required")
    @Size(min = 8, max = 16, message = "Current password must be between 8 and 16 characters")
    private String newPassword;

}

