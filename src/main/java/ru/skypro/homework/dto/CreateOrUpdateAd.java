package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Size;

@Data
@Schema(description = "ДТО для обновления объявления")
public class CreateOrUpdateAd {

    @NonNull
    @Size(min = 4, max = 32)
    private String title;
    @NonNull
    @Size(min = 0, max = 10_000_000)
    private Integer price;
    @NonNull
    @Size(min = 8, max = 64)
    private String description;
}
