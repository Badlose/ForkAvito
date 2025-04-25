package ru.skypro.homework.dto.give;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Schema(description = "ДТО для предоставления информации обо всех комментариях")
public class Comments {

    @NotBlank(message = "Count of Comments is required")
    private Integer count;
    @NotBlank(message = "Comments are required")
    private List<Comment> results;
}
