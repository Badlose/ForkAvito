package ru.skypro.homework.dto.give;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@Schema(description = "ДТО для предоставления информации об объявлении")
public class Ad {

    private Integer author;
    private String image;
    private Integer pk;
    private Integer price;
    private String title;
}
