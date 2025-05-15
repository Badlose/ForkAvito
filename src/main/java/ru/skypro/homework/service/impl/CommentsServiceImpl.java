package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.accept.CreateOrUpdateComment;
import ru.skypro.homework.dto.give.Ad;
import ru.skypro.homework.dto.give.Ads;
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

    /**
     * Get all comments
     * @return {@link Comments}
     */
    @Override
    @Transactional
    public Comments getComments(Integer id) {
        AdEntity adFromDB = getAdFromDB(id);
        List<CommentEntity> comments = adFromDB.getComments();
        return toComments(comments);
    }

    /**
     * Create new ad
     * @param userDetails Authorized user from CustomUserDetails
     * @param id ad`s id
     * @param comment Comment's data
     * @return {@link Comment}
     */
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

    /**
     * Remove Comment from DB
     * @param adId Ad`s id
     * @param commentId Comment`s id
     */
    @Override
    @Transactional
    public void deleteComment(Integer adId, Integer commentId) {
        CommentEntity commentEntity = getCommentEntityFromDb(commentId);
        validateCommentAuthor(commentId, commentEntity);
        commentRepository.deleteById(commentId);
    }

    /**
     * Update comment
     * @param adId Ad`s id
     * @param commentId Comment`s id
     * @param comment Comment`s new data
     * @return {@link Comment}
     */
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

    /**
     * Get User entity from DB
     * @param userDetails Authorized user from CustomUserDetails
     * @return {@link UserEntity}
     */
    private UserEntity getUserEntity(CustomUserDetails userDetails) {
        String username = userDetails.getUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format(username)));
    }

    /**
     * Get Ad entity from DB
     * @param id Ad`s id
     * @return {@link AdEntity}
     */
    private AdEntity getAdFromDB(Integer id) {
        return adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(id));
    }

    /**
     * Validate comment`s author
     * @param commentId comment`s id
     * @param commentEntity comment entity from DB
     */
    private void validateCommentAuthor(Integer commentId, CommentEntity commentEntity) {
        if (!checkAuthority(commentEntity)) {
            throw new CommentAccessNotAllowedException(commentId);
        }
    }

    /**
     * Check user`s right to edit comment
     * @param commentEntity comment form DB
     * @return {@code true} if user is comment`s author, <br>
     * {@code false} otherwise
     */
    private boolean checkAuthority(CommentEntity commentEntity) {
        UserEntity userEntity = getUserEntityFromAuthentication();
        return userEntity.getRole().equals(Role.ADMIN) ||
                userEntity.getId().equals(commentEntity.getUser().getId());
    }

    /**
     * Get user entity from Authentication
     * @return {@link UserEntity}
     */
    private UserEntity getUserEntityFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    /**
     * Get comment entity from DB
     * @param commentId comment`s id
     * @return {@link CommentEntity}
     */
    private CommentEntity getCommentEntityFromDb(Integer commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
    }

    /**
     * Check Ad author in CommentsController methods
     * @param id Comment`s id
     * @return {@code true} if user is comment`s author, <br>
     * {@code false} otherwise
     */
    public boolean checkCommentAuthor(Integer id) {
        CommentEntity commentEntity = getCommentEntityFromDb(id);
        UserEntity userEntity = getUserEntityFromAuthentication();
        Integer userId = userEntity.getId();
        Integer commentId = commentEntity.getUser().getId();
        return userId.equals(commentId);
    }

}
