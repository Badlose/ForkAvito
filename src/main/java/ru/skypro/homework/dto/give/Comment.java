package ru.skypro.homework.dto.give;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "ДТО для предоставления информации о комментарии")
public class Comment {

    @NotBlank(message = "Author is required")
    private Integer author;
    @NotBlank(message = "Author image is required")
    private String authorImage;
    @NotBlank(message = "Author firstname is required")
    private String authorFirstName;
    @NotBlank(message = "Creation time is required")
    private Long createdAt;
    @NotBlank(message = "Primary key is required")
    private Integer pk;
    @NotBlank(message = "Text is required")
    private String text;
}
