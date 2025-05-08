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
import ru.skypro.homework.dto.accept.CreateOrUpdateAd;
import ru.skypro.homework.dto.give.Ad;
import ru.skypro.homework.dto.give.Ads;
import ru.skypro.homework.dto.give.ExtendedAd;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.service.AdsService;

import java.io.IOException;

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
                    @RequestPart("image") MultipartFile image) throws IOException {
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
        adsService.removeAd(id);
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
        return adsService.updateAd(id, updateAd);
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
    public ResponseEntity<byte[]> updateImage(@AuthenticationPrincipal CustomUserDetails userDetails,
                       @PathVariable(required = true) Integer id,
                       @RequestBody MultipartFile image) throws IOException {
        byte[] byteImage = adsService.updateImage(id, image);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(byteImage);
    }

    @GetMapping(value = "/images/{id}", produces = {
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_GIF_VALUE,
            "image/*"
    })
    @PreAuthorize("isAuthenticated()")
    public byte[] getImage(@PathVariable String id) {
//        log.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + id);
        return adsService.getAdImage(id);
//        String mediaType = imageService.getMediaType(id);
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType("application/octet-stream"))
//                .body(bytesImage);
    }

//    @GetMapping(value = "/{id}/{imageId}", produces = {
//            MediaType.IMAGE_PNG_VALUE,
//            MediaType.IMAGE_JPEG_VALUE,
//            MediaType.IMAGE_GIF_VALUE,
//            "image/*"
//    })
//    @PreAuthorize("isAuthenticated()")
//    public byte[] getAdImage(@PathVariable String id, @PathVariable String imageUrl) {
//        return adsService.getCurrentAdImage(id);
//    }
}

