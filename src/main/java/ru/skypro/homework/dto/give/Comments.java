package ru.skypro.homework.dto.give;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "ДТО для предоставления информации обо всех комментариях")
@AllArgsConstructor
public class Comments {

    private Integer count;
    private List<Comment> results;
}
