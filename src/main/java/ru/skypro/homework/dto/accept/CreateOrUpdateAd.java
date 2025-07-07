package ru.skypro.homework.dto.accept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Schema(description = "ДТО для обновления объявления")
public class CreateOrUpdateAd {

    @NotBlank(message = "Title is required")
    @Size(min = 4, max = 32, message = "Title must be between 4 and 32 characters")
    private String title;
    @NotBlank(message = "Price is required")
    @Size(min = 0, max = 10_000_000, message = "Price must be between 0 and 10_000_000")
    private Integer price;
    @NotBlank(message = "Description is required")
    @Size(min = 8, max = 64, message = "Description must be between 8 and 64 characters")
    private String description;
}
