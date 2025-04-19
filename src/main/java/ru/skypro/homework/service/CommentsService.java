package ru.skypro.homework.service;

import org.springframework.http.ResponseEntity;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

public interface CommentsService {

    Comments getComments(Integer id);

    Comment addComment(Integer id, CreateOrUpdateComment comment);

    void deleteComment(Integer adId, Integer commentId);

    Comment updateComment(Integer adId, Integer commentId, CreateOrUpdateComment comment);

}
