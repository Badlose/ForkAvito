package ru.skypro.homework.dto.accept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ДТО для аутентификации пользователя")
public class Login {

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 32, message = "Username must be between 4 and 32 characters")
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 16, message = "Password must be between 4 and 32 characters")
    private String password;
}
