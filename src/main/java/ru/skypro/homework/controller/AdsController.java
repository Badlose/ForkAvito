package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.accept.CreateOrUpdateAd;
import ru.skypro.homework.dto.give.Ad;
import ru.skypro.homework.dto.give.Ads;
import ru.skypro.homework.dto.give.ExtendedAd;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.service.AdsService;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
@Tag(name = "Объявления", description = "API для управления объявлениями")
public class AdsController {


    private final AdsService adsService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Получение всех объявлений",
            tags = {"Объявления"},
            operationId = "getAllAds",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Ads.class)))
            }
    )
    public Ads getAllAds() {
        log.info("Метод getAllAds класса AdsController был вызван ");
        return adsService.getAllAds();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Добавление объявления",
            tags = {"Объявления"},
            operationId = "addAd",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Ad.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = ""))
            }
    )
    public Ad addAd(@AuthenticationPrincipal CustomUserDetails userDetails,
                    @RequestPart("properties") CreateOrUpdateAd createAd,
                    @RequestPart("image") MultipartFile image) {
        return adsService.createNewAd(userDetails, createAd, image);
    }

    @GetMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Получение информации об объявлении",
            tags = {"Объявления"},
            operationId = "getAds",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExtendedAd.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = ""))
            }
    )
    public ExtendedAd getAds(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @PathVariable(required = true) Integer id) {
        return adsService.getAdById(id);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Удаление объявления",
            tags = {"Объявления"},
            operationId = "removeAd",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = ""))
            }
    )
    public void removeAd(@AuthenticationPrincipal CustomUserDetails userDetails,
                         @PathVariable(required = true) Integer id) {
        adsService.removeAd(userDetails, id);
    }

    @PatchMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Обновление информации об объявлении",
            tags = {"Объявления"},
            operationId = "updateAds",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Ad.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = ""))
            }
    )
    public Ad updateAds(@AuthenticationPrincipal CustomUserDetails userDetails,
                        @PathVariable(required = true) Integer id,
                        @RequestBody CreateOrUpdateAd updateAd) {
        return adsService.updateAd(userDetails, id, updateAd);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Получение объявлений авторизованного пользователя",
            tags = {"Объявления"},
            operationId = "getAdsMe",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Ads.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = ""))
            }
    )
    public Ads getAdsMe(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return adsService.getAdsMe(userDetails);
    }

    @PatchMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Обновление картинки объявления",
            tags = {"Объявления"},
            operationId = "updateImage",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = "application/octet-stream",
                                    schema = @Schema(type = "string", format = "binary"))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = ""))
            }
    )
    public MultipartFile updateImage(@AuthenticationPrincipal CustomUserDetails userDetails, // byte[]
                                     @PathVariable(required = true) Integer id,
                                     @RequestBody MultipartFile image) {
        return adsService.updateImage(userDetails, id, image);
    }

}

