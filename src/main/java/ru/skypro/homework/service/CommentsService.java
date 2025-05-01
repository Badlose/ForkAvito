package ru.skypro.homework.service;

import ru.skypro.homework.dto.accept.CreateOrUpdateComment;
import ru.skypro.homework.dto.give.Comment;
import ru.skypro.homework.dto.give.Comments;
import ru.skypro.homework.security.CustomUserDetails;

public interface CommentsService {

    Comments getComments(Integer id);

    Comment addComment(CustomUserDetails userDetails, Integer id, CreateOrUpdateComment comment);

    void deleteComment(CustomUserDetails userDetails, Integer adId, Integer commentId);

    Comment updateComment(CustomUserDetails userDetails, Integer adId, Integer commentId, CreateOrUpdateComment comment);

}
