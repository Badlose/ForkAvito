package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "ДТО для обновления комментария")
public class CreateOrUpdateComment {

    private String text;
}
