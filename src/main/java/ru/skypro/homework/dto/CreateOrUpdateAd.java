package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "ДТО для обновления объявления")
public class CreateOrUpdateAd {

    private String title;
    private Integer price;
    private String description;
}
