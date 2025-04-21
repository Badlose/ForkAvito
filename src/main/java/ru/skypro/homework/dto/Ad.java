package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

@Data
@Schema(description = "ДТО для предоставления информации об объявлении")
public class Ad {

    @NonNull
    private Integer author;
    @NonNull
    private String image;
    @NonNull
    private Integer pk;
    @NonNull
    private Integer price;
    @NonNull
    private String title;
}
