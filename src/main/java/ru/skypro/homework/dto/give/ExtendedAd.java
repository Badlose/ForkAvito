package ru.skypro.homework.dto.give;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "ДТО для предоставления расширенной информации об объявлении и авторе")
public class ExtendedAd {

    @NotBlank(message = "Primary key is required")
    private Integer pk;
    @NotBlank(message = "Author firstname is required")
    private String authorFirstName;
    @NotBlank(message = "Author lastname is required")
    private String authorLastName;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Image is required")
    private String image;
    @NotBlank(message = "Phone number is required")
    private String phone;
    @NotBlank(message = "Price is required")
    private Integer price;
    @NotBlank(message = "Title is required")
    private String title;
}
