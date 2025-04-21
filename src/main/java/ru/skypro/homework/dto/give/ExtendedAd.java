package ru.skypro.homework.dto.give;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

@Data
@Schema(description = "ДТО для предоставления расширенной информации об объявлении и авторе")
public class ExtendedAd {

    @NonNull
    private Integer pk;
    @NonNull
    private String authorFirstName;
    @NonNull
    private String authorLastName;
    @NonNull
    private String description;
    @NonNull
    private String email;
    @NonNull
    private String image;
    @NonNull
    private String phone;
    @NonNull
    private Integer price;
    @NonNull
    private String title;
}
