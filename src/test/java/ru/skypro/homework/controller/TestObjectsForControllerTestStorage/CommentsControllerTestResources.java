//package ru.skypro.homework.controller.TestObjectsForControllerTestStorage;
//
//import ru.skypro.homework.dto.give.Comment;
//import ru.skypro.homework.dto.give.Comments;
//import ru.skypro.homework.dto.accept.CreateOrUpdateComment;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public abstract class CommentsControllerTestResources {
//
//    protected Comment getTestComment() {
//        Integer id = 1;
//        String authorImage = "comment author image";
//        String authorFirstName  = "comment author first name";
//        Long createAt = 1L;
//        Integer pk = 1;
//        String text = "comment text";
//        return new Comment(id, authorImage, authorFirstName, createAt, pk, text);
//    }
//
//    protected Comments getTestComments() {
//        Integer id = 1;
//        String authorImage = "comment author image";
//        String authorFirstName  = "comment author first name";
//        Long createAt = 1L;
//        Integer pk = 1;
//        String text = "comment text";
//        Comment comment = new Comment(id, authorImage, authorFirstName, createAt, pk, text);
//
//        List<Comment> commentList = new ArrayList<>(List.of(comment));
//
//        Integer commentsCount = 1;
//        return new Comments(commentsCount, commentList);
//    }
//
//    protected CreateOrUpdateComment getTestCreateOrUpdateComment() {
//        String commentText = "created or updated comment text";
//        return new CreateOrUpdateComment(commentText);
//    }
//}
