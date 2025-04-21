package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

@Data
@Schema(description = "ДТО для обновления объявления")
public class CreateOrUpdateAd {

    @NonNull
    private String title;
    @NonNull
    private Integer price;
    @NonNull
    private String description;
}
