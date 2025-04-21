package ru.skypro.homework.dto.give;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@Schema(description = "ДТО для предоставления информации о всех объявлениях")
public class Ads {

    @NonNull
    private Integer count;
    @NonNull
    private List<Ad> result;
}
