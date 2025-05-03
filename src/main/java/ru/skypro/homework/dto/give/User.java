package ru.skypro.homework.dto.give;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.skypro.homework.dto.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@Schema(description = "ДТО для получения информации об авторизованном пользователе")
public class User {

    @NotEmpty(message = "User id is required")
    private Integer id;
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "User firstname is required")
    private String firstName;
    @NotBlank(message = "User lastname is required")
    private String lastName;
    @NotBlank(message = "Phone number is required")
    private String phone;
    @NotEmpty(message = "Role is required")
    private Role role;
    private String image;
}
