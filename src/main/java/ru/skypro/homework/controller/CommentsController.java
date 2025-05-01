package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.accept.CreateOrUpdateComment;
import ru.skypro.homework.dto.give.Comment;
import ru.skypro.homework.dto.give.Comments;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.service.CommentsService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
@Tag(name = "Комментарии", description = "API для управления комментариями")
public class CommentsController {

    private final CommentsService commentsService;

    @GetMapping("{id}/comments")
    @PreAuthorize("isAuthenticated()")
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

    @PostMapping("{id}/comments")
    @PreAuthorize("isAuthenticated()")
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
    public ResponseEntity<Comment> addComment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                              @RequestParam(required = true) Integer id,
                                              @RequestBody CreateOrUpdateComment newComment) {
        return ResponseEntity.ok(commentsService.addComment(userDetails, id, newComment));
    }

    @DeleteMapping("{adId}/comments/{commentId}")
    @PreAuthorize("isAuthenticated()")
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
    public void deleteComment(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @RequestParam(required = true) Integer adId,
                              @RequestParam(required = true) Integer commentId) {
        commentsService.deleteComment(userDetails, adId, commentId);
    }

    @PatchMapping("{adId}/comments/{commentId}")
    @PreAuthorize("isAuthenticated()")
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
    public ResponseEntity<Comment> updateComment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                 @RequestParam(required = true) Integer adId,
                                                 @RequestParam(required = true) Integer commentId,
                                                 @RequestBody CreateOrUpdateComment updatedComment) {
        return ResponseEntity.ok(commentsService.updateComment(userDetails, adId, commentId, updatedComment));
    }

}
