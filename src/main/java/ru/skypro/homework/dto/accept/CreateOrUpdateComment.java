package ru.skypro.homework.dto.accept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Size;

@Data
@Schema(description = "ДТО для обновления комментария")
public class CreateOrUpdateComment {

    @NonNull
    @Size(min = 8, max = 64)
    private String text;
}
