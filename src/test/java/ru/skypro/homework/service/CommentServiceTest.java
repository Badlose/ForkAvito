package ru.skypro.homework.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.accept.CreateOrUpdateComment;
import ru.skypro.homework.dto.give.Comment;
import ru.skypro.homework.dto.give.Comments;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.service.impl.CommentsServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.skypro.NewTypeTesting.TestHelper.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CommentServiceTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private CommentsServiceImpl service;

    @BeforeEach
//    @Transactional
    void setUpData() {
        UserEntity preparedUserEntity = createUserEntityBuilder()
                .id(null)
                .username("username")
                .build();
        userRepository.save(preparedUserEntity);

        AdEntity adEntity = createAdEntityBuilder()
                .pk(null)
                .user(preparedUserEntity)
                .build();
        adRepository.save(adEntity);

        preparedUserEntity.setAds(List.of(adEntity));

        CommentEntity commentEntity = createCommentEntityBuilder()
                .pk(null)
                .user(preparedUserEntity)
                .ad(adEntity)
                .build();
        commentRepository.save(commentEntity);
        adEntity.setComments(List.of(commentEntity));
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        adRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
//    @Transactional
    void shouldGetComments() {
        UserEntity userEntity = userRepository.findByUsername("username").orElseThrow();
        AdEntity adEntity = adRepository.findByUserId(userEntity.getId());
        CommentEntity commentEntity = adEntity.getComments().get(0);

        Comments comments = service.getComments(adEntity.getPk());
        Comment commentFromDb = comments.getResults().get(0);

        assertThat(comments).isNotNull();
        assertThat(commentFromDb.getAuthor()).isEqualTo(commentEntity.getUser().getId());
        assertThat(commentFromDb.getPk()).isEqualTo(commentEntity.getPk());
        assertThat(commentFromDb.getText()).isEqualTo(commentEntity.getText());
    }

    @Test
    void shouldAddComment() {
        String username = "username";
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow();
        AdEntity adEntity = adRepository.findByUserId(userEntity.getId());
        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
        Integer adId = adEntity.getPk();
        CreateOrUpdateComment createComment = getCreateOrUpdateComment();

        Comment createdComment = service.addComment(userDetails, adId, createComment);

        List<CommentEntity> commentEntityList = commentRepository.findAll();
        CommentEntity commentEntity = commentEntityList.get(1);

        assertThat(createdComment.getAuthor()).isEqualTo(commentEntity.getUser().getId());
        assertThat(createdComment.getCreatedAt()).isEqualTo(commentEntity.getCreatedAt());
        assertThat(createdComment.getPk()).isEqualTo(commentEntity.getPk());
        assertThat(createdComment.getText()).isEqualTo(commentEntity.getText());
    }

//    @Test
//    @Transactional
//    void shouldDeleteComment() { //todo
//        String username = "username";
//        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow();
//        AdEntity adEntity = adRepository.findByUserId(userEntity.getId());
//
//
//        System.out.println(adEntity);
//        System.out.println(userEntity);
//
//        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
//        Authentication authentication = new TestAuthentication(userDetails);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        Integer adId = adEntity.getPk();
//        Integer commentId = adEntity.getComments().get(0).getPk();
//
//        service.deleteComment(adId, commentId);
//
//        assertThat(commentRepository.existsById(commentId)).isFalse();
//    }

    @Test
    void shouldUpdateComment() {
        String username = "username";
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow();
        AdEntity adEntity = adRepository.findByUserId(userEntity.getId());
        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
        Authentication authentication = new TestAuthentication(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Integer adId = adEntity.getPk();
        Integer commentId = adEntity.getComments().get(0).getPk();
        CreateOrUpdateComment comment = getCreateOrUpdateComment();

        service.updateComment(adId, commentId, comment);

        CommentEntity commentFromDb = commentRepository.findById(commentId).orElseThrow();

        assertThat(comment.getText()).isEqualTo(commentFromDb.getText());
    }

}
