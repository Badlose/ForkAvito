package ru.skypro.homework.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.TestObjectStorage.CommentsControllerTestResources;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentsService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentsControllerAdsControllerTest extends CommentsControllerTestResources {
    @Mock
    private CommentsService service;
    @InjectMocks
    private CommentsController controller;
    private static final Integer commentId = 1;
    private static final Integer adId = 2;

    @Test
    void shouldGetComments() {

        Comments expectedComments = getTestComments();

        when(service.getComments(commentId)).thenReturn(expectedComments);

        Comments actualComments = controller.getComments(commentId).getBody();

        assertEquals(expectedComments, actualComments);
        verify(service, times(1)).getComments(commentId);
    }

    @Test
    void shouldAddComment() {

        CreateOrUpdateComment createdComment = getTestCreateOrUpdateComment();
        Comment expectedComment = getTestComment();

        when(service.addComment(commentId, createdComment)).thenReturn(expectedComment);

        Comment actualComment = controller.addComment(commentId, createdComment).getBody();

        assertEquals(expectedComment, actualComment);
        verify(service, times(1)).addComment(commentId, createdComment);
    }


    //для разбора на консультации

//    @Test
//    void shouldDeleteComment() {
//        ResponseEntity<?> expectedResponseEntity = ResponseEntity.ok().build();
//
//        doNothing().when(service).deleteComment(adId, commentId);
//
//        ResponseEntity<?> actualResponseEntity = controller.deleteComment(adId, commentId);
//
//        assertEquals(expectedResponseEntity, actualResponseEntity);
//        verify(service, times(1)).deleteComment(adId, commentId);
//    }

    @Test
    void shouldUpdateComment() {
        CreateOrUpdateComment updateComment = getTestCreateOrUpdateComment();
        Comment expectedComment = getTestComment();

        when(service.updateComment(adId, commentId, updateComment)).thenReturn(expectedComment);

        Comment actualComment = controller.updateComment(adId, commentId, updateComment).getBody();

        assertEquals(expectedComment, actualComment);
        verify(service, times(1)).updateComment(adId, commentId, updateComment);
    }

}
