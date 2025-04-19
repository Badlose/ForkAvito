package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "ДТО для предоставления информации о всех объявлениях")
public class Ads {

    private Integer count;
    private List<Ad> result;
}
