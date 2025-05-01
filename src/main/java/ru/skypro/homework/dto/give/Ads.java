package ru.skypro.homework.dto.give;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "ДТО для предоставления информации о всех объявлениях")
public class Ads {

    @NotEmpty(message = "Count of Ads is required")
    private Integer count;
    @NotNull
    private List<Ad> result;
}
