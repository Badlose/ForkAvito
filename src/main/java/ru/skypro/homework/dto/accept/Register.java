package ru.skypro.homework.dto.accept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.dto.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Schema(description = "ДТО для регистрации нового пользователя")
@NoArgsConstructor
@AllArgsConstructor
public class Register {

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 32, message = "Username must be between 4 and 32 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 16, message = "Password must be between 8 and 15 characters")
    private String password;

    @NotBlank(message = "Firstname is required")
    @Size(min = 2, max = 16, message = "Firstname must be between 2 and 16 characters")
    private String firstName;

    @NotBlank(message = "Lastname is required")
    @Size(min = 2, max = 16, message = "Lastname must be between 2 and 16 characters")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}", message = "Неверный формат номера телефона")
    private String phone;

    @NotBlank(message = "Role is required")
    private Role role;
}
