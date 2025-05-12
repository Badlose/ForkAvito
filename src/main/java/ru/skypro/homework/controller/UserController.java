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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.accept.NewPassword;
import ru.skypro.homework.dto.give.User;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Пользователи", description = "API для управления пользовательской информацией")
public class UserController {

    private final UserService userService;

    @PostMapping("/set_password")
    @PreAuthorize("isAuthenticated()")
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
    public void setPassword(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @RequestBody NewPassword newPassword) {
        userService.setPassword(userDetails, newPassword);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
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
    public User getUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userService.getUserSelfInfo(userDetails);
    }

    @PatchMapping("/me")
    @PreAuthorize("isAuthenticated()")
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
    public UpdateUser updateUser(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @RequestBody UpdateUser updateUser) {
        return userService.updateUser(userDetails, updateUser);
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    @Operation(tags = {"Пользователи"},
            summary = "Обновление аватара авторизованного пользователя",
            operationId = "updateUserImage",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = ""))
            }
    )
    public void updateUserImage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @RequestBody MultipartFile image) {
        userService.updateUserImage(userDetails, image);
    }

    @GetMapping(value = "/images/{id}", produces = {
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_GIF_VALUE,
            "image/*"
    })
    @PreAuthorize("isAuthenticated()")
    public byte[] getImage(@PathVariable String id) {
        return userService.getUserImage(id);
    }
}
