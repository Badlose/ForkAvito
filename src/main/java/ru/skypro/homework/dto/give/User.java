package ru.skypro.homework.dto.give;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.skypro.homework.dto.Role;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "ДТО для получения информации об авторизованном пользователе")
public class User {

    @NotBlank(message = "User id is required")
    private Integer id;
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "User firstname is required")
    private String firstName;
    @NotBlank(message = "User lastname is required")
    private String lastName;
    @NotBlank(message = "Phone number is required")
    private String phone;
    @NotBlank(message = "Role is required")
    private Role role;
    @NotBlank(message = "Image is required")
    private String image;
}
