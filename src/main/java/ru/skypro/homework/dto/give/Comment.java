package ru.skypro.homework.dto.give;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

@Data
@Schema(description = "ДТО для предоставления информации о комментарии")
public class Comment {

    @NonNull
    private Integer author;
    @NonNull
    private String authorImage;
    @NonNull
    private String authorFirstName;
    @NonNull
    private Long createdAt;
    @NonNull
    private Integer pk;
    @NonNull
    private String text;
}
