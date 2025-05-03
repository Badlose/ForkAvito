package ru.skypro.homework.dto.give;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@Schema(description = "ДТО для предоставления информации об объявлении")
public class Ad {

    @NotEmpty(message = "Author is required")
    private Integer author;
//    @NotNull
    private String image;
    @NotEmpty(message = "Primary key is required")
    private Integer pk;
    @NotEmpty(message = "Price is required")
    private Integer price;
    @NotBlank(message = "Title is required")
    private String title;
}
