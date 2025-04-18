package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentsService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class CommentsController {

    private final CommentsService commentsService;

    @GetMapping("{id}/comments")
    public Comments getComments(@RequestParam(required = true) Integer id) {
        return commentsService.getComments(id);
    }

    @PostMapping("{id}/comments")
    public Comment addComment(@RequestParam(required = true) Integer id,
                              @RequestBody CreateOrUpdateComment comment) {
        return commentsService.addComment(id, comment);
    }

    @DeleteMapping("{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@RequestParam(required = true) Integer adId,
                                           @RequestParam(required = true) Integer commentId) {
        return commentsService.deleteComment(adId, commentId);
    }

    @PatchMapping("{adId}/comments/{commentId}")
    public Comment updateComment(@RequestParam(required = true) Integer adId,
                                 @RequestParam(required = true) Integer commentId,
                                 @RequestBody CreateOrUpdateComment comment) {
        return commentsService.updateComment(adId, commentId, comment);
    }
    }
