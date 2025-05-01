package ru.skypro.homework.dto.give;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(description = "ДТО для предоставления информации обо всех комментариях")
@AllArgsConstructor
public class Comments {

    @NotEmpty(message = "Count of Comments is required")
    private Integer count;
    @NotNull
    private List<Comment> results;
}
