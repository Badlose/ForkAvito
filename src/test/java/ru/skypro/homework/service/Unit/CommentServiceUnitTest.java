package ru.skypro.homework.service.Unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.accept.CreateOrUpdateComment;
import ru.skypro.homework.dto.give.Comment;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.service.impl.CommentsServiceImpl;
import ru.skypro.homework.service.impl.ImageServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.skypro.homework.helper.TestHelper.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceUnitTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AdRepository adRepository;
    @InjectMocks
    private CommentsServiceImpl service;


    @Test
    void shouldAddComment() { //todo время создания комментария
        UserEntity userEntity = createUserEntity();
        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
        AdEntity adEntity = createAdEntityBuilder()
                .pk(1)
                .user(userEntity)
                .build();
        Integer adId = adEntity.getPk();
        CreateOrUpdateComment createComment = getCreateOrUpdateComment();
        CommentEntity commentEntity = CommentMapper.createComment(userEntity, adEntity, createComment);

        when(adRepository.findById(adId)).thenReturn(Optional.of(adEntity));
        when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.of(userEntity));
        when(commentRepository.save(ArgumentMatchers.any(CommentEntity.class))).thenReturn(any(CommentEntity.class));

        Comment comment = service.addComment(userDetails, adId, createComment);

        verify(commentRepository).save(ArgumentMatchers.any(CommentEntity.class));
        assertThat(comment.getText()).isEqualTo(commentEntity.getText());
    }

    @Test
    void shouldDeleteComment() {
        UserEntity userEntity = createUserEntity();
        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
        Authentication authentication = new TestAuthentication(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AdEntity adEntity = createAdEntityBuilder()
                .pk(1)
                .user(userEntity)
                .build();
        Integer adId = adEntity.getPk();
        CommentEntity commentEntity = createCommentEntityBuilder()
                .ad(adEntity)
                .user(userEntity)
                .build();
        Integer commentId = commentEntity.getPk();

        when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.of(userEntity));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(commentEntity));
        doNothing().when(commentRepository).deleteById(commentId);

        service.deleteComment(adId, commentId);

        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    @Transactional
    void shouldUpdateComment() {
        UserEntity userEntity = createUserEntity();
        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
        Authentication authentication = new TestAuthentication(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AdEntity adEntity = createAdEntityBuilder()
                .pk(1)
                .user(userEntity)
                .build();
        Integer adId = adEntity.getPk();
        CommentEntity commentEntity = createCommentEntityBuilder()
                .ad(adEntity)
                .user(userEntity)
                .build();
        Integer commentId = commentEntity.getPk();
        CreateOrUpdateComment updateComment = getCreateOrUpdateComment();
        CommentEntity entityBefore = createCommentEntityBuilder()
                .ad(adEntity)
                .user(userEntity)
                .text(updateComment.getText())
                .build();

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(commentEntity));
        when(commentRepository.save(ArgumentMatchers.any(CommentEntity.class))).thenReturn(any(CommentEntity.class));

        Comment updatedComment = service.updateComment(adId, commentId, updateComment);

        verify(commentRepository).save(ArgumentMatchers.any(CommentEntity.class));
        assertThat(updatedComment.getText()).isEqualTo(entityBefore.getText());
    }
}
