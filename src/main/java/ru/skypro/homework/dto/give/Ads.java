package ru.skypro.homework.dto.give;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Schema(description = "ДТО для предоставления информации о всех объявлениях")
public class Ads {

    @NotBlank(message = "Count of Ads is required")
    private Integer count;
    @NotBlank(message = "Ads are required")
    private List<Ad> result;
}
