package ru.skypro.homework.dto.give;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@Schema(description = "ДТО для предоставления информации обо всех комментариях")
public class Comments {

    @NonNull
    private Integer count;
    @NonNull
    private List<Comment> results;
}
