package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.service.UserService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Пользователи", description = "API для управления пользовательской информацией")
public class UserController {

    private final UserService userService;

    /**
     * Метод для обновления пароля
     *
     * @param newPassword - объект, содержащий информацию о новом пароле
     * @return HttpStatus
     */
    @PostMapping("/set_password")
    @Operation(summary = "Обновление пароля",
            tags = {"Пользователи"},
            operationId = "setPassword",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NewPassword.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = ""))
            }
    )
    public ResponseEntity<?> setPassword(@RequestBody NewPassword newPassword) {
        userService.setPassword(newPassword);
        return ResponseEntity.ok("Password successfully changed");
    }

    /**
     * Метод для получения информации об авторизованном пользователе
     *
     * @return Объект User, содержащий информацию об авторизованном пользователе
     */
    @GetMapping("/me")
    @Operation(tags = {"Пользователи"},
            summary = "Получение информации об авторизованном пользователе",
            operationId = "getUser",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = ""))
            }
    )
    public ResponseEntity<User> getUser() {
        return ResponseEntity.ok(userService.getUserSelfInfo());
    }

    /**
     * Метод для обновления информации об авторизованном пользователе
     *
     * @param updateUser - объект, содержащий обновленную информацию о пользователе
     * @return Объект UpdateUser, содержащий обновленную информацию о пользователе
     */
    @PatchMapping("/me")
    @Operation(tags = {"Пользователи"},
            summary = "Обновление информации об авторизованном пользователе",
            operationId = "updateUser",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateUser.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UpdateUser.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = ""))
            }
    )
    public ResponseEntity<UpdateUser> updateUser(@RequestBody UpdateUser updateUser) {
        return ResponseEntity.ok(userService.updateUser(updateUser));
    }

    /**
     * Метод для обновления аватара авторизованного пользователя
     *
     * @param image - изображение аватара
     * @return HttpStatus
     */
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(tags = {"Пользователи"},
            summary = "Обновление аватара авторизованного пользователя",
            operationId = "updateUserImage",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "multipart/form-data",
                            schema = @Schema(implementation = UpdateUser.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UpdateUser.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = ""))
            }
    )
    public ResponseEntity<?> updateUserImage(@RequestBody MultipartFile image) {
        userService.updateUserImage(image);
        return ResponseEntity.ok("Image successfully updated");
    }

}
