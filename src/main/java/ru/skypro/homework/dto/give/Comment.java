package ru.skypro.homework.dto.give;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@Builder
@Schema(description = "ДТО для предоставления информации о комментарии")
public class Comment {

    @NotEmpty(message = "Author is required")
    private Integer author;
    private String authorImage;
    @NotBlank(message = "Author firstname is required")
    private String authorFirstName;
    @NotEmpty(message = "Creation time is required")
    private Instant createdAt;
    @NotEmpty(message = "Primary key is required")
    private Integer pk;
    @NotBlank(message = "Text is required")
    private String text;
}
