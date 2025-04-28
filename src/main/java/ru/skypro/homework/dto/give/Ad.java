package ru.skypro.homework.dto.give;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "ДТО для предоставления информации об объявлении")
public class Ad {

    @NotBlank(message = "Author is required")
    private Integer author;
    @NotNull
    private String image;
    @NotBlank(message = "Primary key is required")
    private Integer pk;
    @NotBlank(message = "Price is required")
    private Integer price;
    @NotBlank(message = "Title is required")
    private String title;
}
