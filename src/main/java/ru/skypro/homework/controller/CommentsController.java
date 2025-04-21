package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.give.Comment;
import ru.skypro.homework.dto.give.Comments;
import ru.skypro.homework.dto.accept.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentsService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
@Tag(name = "Комментарии", description = "API для управления комментариями")
public class CommentsController {

    private final CommentsService commentsService;

    /**
     * Метод для получения всех комментариев объявления
     *
     * @param id - уникальный идентификатор объявления
     * @return Объект Comments, содержащий список комментариев данного объявления
     */
    @GetMapping("{id}/comments")
    @Operation(summary = "Получение комментариев объявления",
            tags = {"Комментарии"},
            operationId = "getComments",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Comments.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = ""))
            }
    )
    public ResponseEntity<Comments> getComments(@RequestParam(required = true) Integer id) {
        return ResponseEntity.ok(commentsService.getComments(id));
    }

    /**
     * Метод для добавления нового комментария к объявлению
     *
     * @param id         - уникальный идентификатор объявления
     * @param newComment - объект, содержащий информацию о новом комментарии
     * @return Объект Comment, содержаций информациюю о новом добавленном комменраии
     */
    @PostMapping("{id}/comments")
    @Operation(summary = "Добавление комментария к объявлению",
            tags = {"Комментарии"},
            operationId = "addComment",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Comment.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = ""))
            }
    )
    public ResponseEntity<Comment> addComment(@RequestParam(required = true) Integer id,
                                              @RequestBody CreateOrUpdateComment newComment) {
        return ResponseEntity.ok(commentsService.addComment(id, newComment));
    }

    /**
     * Метод для удаления комментария
     *
     * @param adId      - уникальный идентификатор объявления
     * @param commentId - уникальный идентификатор комментария
     * @return HttpStatus
     */
    @DeleteMapping("{adId}/comments/{commentId}")
    @Operation(summary = "Удаление комментария",
            tags = {"Комментарии"},
            operationId = "deleteComment",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = ""))
            }
    )
    public void deleteComment(@RequestParam(required = true) Integer adId,
                                           @RequestParam(required = true) Integer commentId) {
        commentsService.deleteComment(adId, commentId);
    }

    /**
     * Метод для обновления уже существующего комментария
     *
     * @param adId            - уникальный идентификатор объявления
     * @param commentId       - уникальный идентификатор комментария
     * @param updatedComment- объект, содержащий информацию об обновленном комментарии
     * @return Объект Comment, содержаций информациюю об обновленном комментарии
     */
    @PatchMapping("{adId}/comments/{commentId}")
    @Operation(summary = "Обновление комментария",
            tags = {"Комментарии"},
            operationId = "updateComment",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Comment.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = ""))
            }
    )
    public ResponseEntity<Comment> updateComment(@RequestParam(required = true) Integer adId,
                                                 @RequestParam(required = true) Integer commentId,
                                                 @RequestBody CreateOrUpdateComment updatedComment) {
        return ResponseEntity.ok(commentsService.updateComment(adId, commentId, updatedComment));
    }

}
