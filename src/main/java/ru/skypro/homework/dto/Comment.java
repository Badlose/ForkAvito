package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "ДТО для предоставления информации о комментарии")
public class Comment {

    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private Long createdAt;
    private Integer pk;
    private String text;
}
