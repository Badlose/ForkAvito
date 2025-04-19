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
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.service.AdsService;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
@Tag(name = "Объявления", description = "API для управления объявлениями")
public class AdsController {

    private AdsService adsService;

    /**
     * Метод для получения всех объявлений
     *
     * @return Объект Ads, содержаций список всех объявлений
     */
    @GetMapping
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
    public ResponseEntity<Ads> getAllAds() {
        log.info("Метод getAllAds класса AdsController был вызван ");
        return ResponseEntity.ok(adsService.getAllAds());
    }

    /**
     * Метод для добавления нового объявления
     *
     * @param createAd - объект содержащий инфолрмацию об объявлении
     * @param image    - изображение объявления
     * @return Объект Ad, содержащий новое объявление
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
    public ResponseEntity<Ad> addAd(@RequestPart("properties") CreateOrUpdateAd createAd,
                                    @RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok(adsService.createNewAd(createAd, image));
    }

    /**
     * Метод для получения информации об объявлении
     *
     * @param id - уникальный идентификатор объявления
     * @return Объект ExtendedAd, содержащий расширенную информацию об объявлении и авторе
     */
    @GetMapping("{id}")
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
    public ResponseEntity<ExtendedAd> getAds(@RequestParam(required = true) Integer id) {
        return ResponseEntity.ok(adsService.getAdById(id));
    }

    /**
     * Метод для удаления объявления
     *
     * @param id - уникальный идентификатор объявления
     * @return HttpStatus
     */
    @DeleteMapping("{id}")
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
    public ResponseEntity<?> removeAd(@RequestParam(required = true) Integer id) {
        adsService.removeAd(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    /**
     * Метод для обновления информации об объявлении
     *
     * @param id       - уникальный идентификатор объявления
     * @param updateAd - объект, содержащий информацию об объявлении
     * @return Объект Ad, содержащий обновленное объявление
     */
    @PatchMapping("{id}")
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
    public ResponseEntity<Ad> updateAds(@RequestParam(required = true) Integer id,
                                        @RequestBody CreateOrUpdateAd updateAd) {
        return ResponseEntity.ok(adsService.updateAd(id, updateAd));
    }

    /**
     * Метод для получения объявлений авторизованного пользователя
     *
     * @return Объект Ads, содержащий список всех объявлениях данного автора
     */
    @GetMapping("/me")
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
    public ResponseEntity<Ads> getAdsMe() {
        return ResponseEntity.ok(adsService.getAdsMe());
    }

    /**
     * Метод для обновления картинки объявления
     *
     * @param id    - уникальный идентификатор объявления
     * @param image - изображение объявления
     * @return Строка с изображением в бинарном формате
     */
    @PatchMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
    public ResponseEntity<MultipartFile> updateImage(@RequestParam(required = true) Integer id,
                                                @RequestBody MultipartFile image) {
        return ResponseEntity.ok(adsService.updateImage(id, image));
    }

}

