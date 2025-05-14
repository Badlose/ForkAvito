package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.accept.CreateOrUpdateComment;
import ru.skypro.homework.dto.give.Comment;
import ru.skypro.homework.dto.give.Comments;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.CommentAccessNotAllowedException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.service.CommentsService;

import java.util.List;

import static ru.skypro.homework.mapper.CommentMapper.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AdRepository adRepository;

    @Override
    @Transactional
    public Comments getComments(Integer id) {
        AdEntity adFromDB = getAdFromDB(id);
        List<CommentEntity> comments = adFromDB.getComments();
        return toComments(comments);
    }

    @Override
    @Transactional
    public Comment addComment(CustomUserDetails userDetails, Integer id, CreateOrUpdateComment comment) {
        AdEntity adFromDB = getAdFromDB(id);
        UserEntity userEntity = getUserEntity(userDetails);
        CommentEntity commentEntity = createComment(userEntity, adFromDB, comment);
        commentRepository.save(commentEntity);
        log.info("New comment was added for Ad with id: {}", id);
        return toComment(commentEntity);
    }

    @Override
    @Transactional
    public void deleteComment(Integer adId, Integer commentId) {
        CommentEntity commentEntity = getCommentEntityFromDb(commentId);
        validateCommentAuthor(commentId, commentEntity);
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public Comment updateComment(Integer adId, Integer commentId, CreateOrUpdateComment comment) {
        CommentEntity commentEntity = getCommentEntityFromDb(commentId);
        validateCommentAuthor(commentId, commentEntity);
        commentEntity.setText(comment.getText());
        commentRepository.save(commentEntity);
        log.info("Comment was updated for Ad with id: {}", adId);
        return toComment(commentEntity);
    }

    @Transactional
    private UserEntity getUserEntity(CustomUserDetails userDetails) {
        String username = userDetails.getUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format(username)));
    }

    @Transactional
    private AdEntity getAdFromDB(Integer id) {
        return adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(id));
    }


    private void validateCommentAuthor(Integer commentId, CommentEntity commentEntity) {
        if (!checkAuthority(commentEntity)) {
            throw new CommentAccessNotAllowedException(commentId);
        }
    }

    private boolean checkAuthority(CommentEntity commentEntity) {
        UserEntity userEntity = getUserEntityFromAuthentication();
        return userEntity.getRole().equals(Role.ADMIN) ||
                userEntity.getId().equals(commentEntity.getUser().getId());
    }

    private UserEntity getUserEntityFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    private CommentEntity getCommentEntityFromDb(Integer commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
    }


    public boolean checkCommentAuthor(Integer id) {
        CommentEntity commentEntity = getCommentEntityFromDb(id);
        UserEntity userEntity = getUserEntityFromAuthentication();
        Integer userId = userEntity.getId();
        Integer commentId = commentEntity.getUser().getId();
        return userId.equals(commentId);
    }

}
