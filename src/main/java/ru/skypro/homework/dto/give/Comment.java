package ru.skypro.homework.dto.give;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@Schema(description = "ДТО для предоставления информации о комментарии")
public class Comment {

    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private Instant createdAt;
    private Integer pk;
    private String text;
}
