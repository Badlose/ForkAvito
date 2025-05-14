package ru.skypro.homework.dto.give;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "ДТО для предоставления информации о всех объявлениях")
public class Ads {

    private Integer count;
    private List<Ad> results;
}
