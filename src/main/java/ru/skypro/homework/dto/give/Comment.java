package ru.skypro.homework.dto.give;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Schema(description = "ДТО для предоставления информации о комментарии")
public class Comment {

    @NotBlank(message = "Author is required")
    private Integer author;
    @NotNull(message = "Author image is required")
    private String authorImage;
    @NotBlank(message = "Author firstname is required")
    private String authorFirstName;
    @NotBlank(message = "Creation time is required")
    private Instant createdAt;
    @NotBlank(message = "Primary key is required")
    private Integer pk;
    @NotBlank(message = "Text is required")
    private String text;
}
