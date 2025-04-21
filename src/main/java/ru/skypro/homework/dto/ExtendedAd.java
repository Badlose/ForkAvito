package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Schema(description = "ДТО для предоставления расширенной информации об объявлении и авторе")
public class ExtendedAd {

    @NonNull
    private Integer pk;
    @NonNull
    @Size(min = 3, max = 10)
    private String authorFirstName;
    @NonNull
    @Size(min = 3, max = 10)
    private String authorLastName;
    @NonNull
    private String description;
    @NonNull
    private String email;
    @NonNull
    private String image;
    @NonNull
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}", message = "еверный формат номера телефона")
    private String phone;
    @NonNull
    private Integer price;
    @NonNull
    private String title;
}
