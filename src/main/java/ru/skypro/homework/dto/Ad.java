package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "ДТО для предоставления информации об объявлении")
public class Ad {

    private Integer author;
    private String image;
    private Integer pk;
    private Integer price;
    private String title;
}
