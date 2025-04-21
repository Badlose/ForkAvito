package ru.skypro.homework.service;

import ru.skypro.homework.dto.give.Comment;
import ru.skypro.homework.dto.give.Comments;
import ru.skypro.homework.dto.accept.CreateOrUpdateComment;

public interface CommentsService {

    Comments getComments(Integer id);

    Comment addComment(Integer id, CreateOrUpdateComment comment);

    void deleteComment(Integer adId, Integer commentId);

    Comment updateComment(Integer adId, Integer commentId, CreateOrUpdateComment comment);

}
