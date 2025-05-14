package ru.skypro.homework.exception.enums;

import io.swagger.v3.oas.annotations.media.Schema;

public enum ErrorType {
    @Schema(name = "Пользователь не найден по айди")
    USER_NOT_FOUND_BY_USERNAME,
    @Schema(name = "Объявление не найдено по айди")
    AD_NOT_FOUND_BY_ID,
    @Schema(name = "Комментарий не найден по айди")
    COMMENT_NOT_FOUND_BY_ID,
    @Schema(name = "Изображение не найдено")
    IMAGE_NOT_FOUND,
    @Schema(name = "Ошибка при сохранении изображения")
    PICTURE_SAVING_ERROR,
    @Schema(name = "Ошибка при создании пути")
    FILE_PATH_NOT_CREATED,
    @Schema(name = "Некорректное изображение")
    INCORRECT_IMAGE,
    @Schema(name = "Недостаточно прав доступа для редактирования объявления")
    AD_ACCESS_NOT_ALLOWED,
    @Schema(name = "Недостаточно прав доступа для редактирования комментария")
    COMMENT_ACCESS_NOT_ALLOWED

}
