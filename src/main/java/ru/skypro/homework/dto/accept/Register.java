package ru.skypro.homework.dto.accept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;
import ru.skypro.homework.dto.Role;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Schema(description = "ДТО для регистрации нового пользователя")
public class Register {

    @NonNull
    @Size(min = 4, max = 32)
    private String username;
    @NonNull
    @Size(min = 8, max = 16)
    private String password;
    @NonNull
    @Size(min = 2, max = 16)
    private String firstName;
    @NonNull
    @Size(min = 2, max = 16)
    private String lastName;
    @NonNull
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}", message = "еверный формат номера телефона")
    private String phone;
    @NonNull
    private Role role;
}
