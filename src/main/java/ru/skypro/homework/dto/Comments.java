package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "ДТО для предоставления информации обо всех комментариях")
public class Comments {

    private Integer count;
    private List<Comment> results;
}
