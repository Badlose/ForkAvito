package ru.skypro.homework.dto.accept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Schema(description = "ДТО для обновления комментария")
public class CreateOrUpdateComment {

    @NotBlank(message = "Text is required")
    @Size(min = 8, max = 64, message = "Text must be between 8 and 64 characters")
    private String text;
}
