package ru.skypro.homework.TestObjectStorage;

import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

import java.util.ArrayList;
import java.util.List;

public abstract class CommentsControllerTestResources {

    protected Comment getTestComment() {
        Comment comment = new Comment();
        comment.setAuthor(1);
        comment.setAuthorImage("comment author image");
        comment.setAuthorFirstName("comment author first name");
        comment.setCreatedAt(1L);
        comment.setPk(1);
        comment.setText("comment text");
        return comment;
    }

    protected Comments getTestComments() {
        Comment comment = new Comment();
        comment.setAuthor(1);
        comment.setAuthorImage("comment author image");
        comment.setAuthorFirstName("comment author first name");
        comment.setCreatedAt(1L);
        comment.setPk(1);
        comment.setText("comment text");

        List<Comment> commentList = new ArrayList<>(List.of(comment));

        Comments comments = new Comments();
        comments.setCount(1);
        comments.setResults(commentList);

        return comments;
    }

    protected CreateOrUpdateComment getTestCreateOrUpdateComment() {
        CreateOrUpdateComment comment = new CreateOrUpdateComment();
        comment.setText("created or updated comment text");
        return comment;
    }
}
