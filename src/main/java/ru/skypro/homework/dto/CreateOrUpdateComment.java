package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

@Data
@Schema(description = "ДТО для обновления комментария")
public class CreateOrUpdateComment {

    @NonNull
    private String text;
}
