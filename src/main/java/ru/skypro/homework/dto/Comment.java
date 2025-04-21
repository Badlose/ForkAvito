package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Size;

@Data
@Schema(description = "ДТО для предоставления информации о комментарии")
public class Comment {

    @NonNull
    private Integer author;
    @NonNull
    private String authorImage;
    @NonNull
    @Size(min = 3, max = 10)
    private String authorFirstName;
    @NonNull
    private Long createdAt;
    @NonNull
    private Integer pk;
    @NonNull
    private String text;
}
