package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.security.CustomUserDetailsService;
import ru.skypro.homework.service.CommentsService;

import java.time.Instant;
import java.util.List;

import static ru.skypro.homework.mapper.CommentMapper.*;

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
        return toComment(commentEntity);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or (hasRole('ROLE_USER') and checkAdAuthorId(#adId))")
    public void deleteComment(CustomUserDetails userDetails, Integer adId, Integer commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or (hasRole('ROLE_USER') and checkAdAuthorId(#adId))")
    public Comment updateComment(CustomUserDetails userDetails, Integer adId, Integer commentId,
                                 CreateOrUpdateComment comment) {

        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(
                        String.format("Comment with id %d not found", commentId)
                ));

        commentEntity.setText(comment.getText());

        commentRepository.save(commentEntity);

        return toComment(commentEntity);
    }

    @Transactional
    private UserEntity getUserEntity(CustomUserDetails userDetails) {
        String username = userDetails.getUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User %s not found", username)));
    }

    @Transactional
    private AdEntity getAdFromDB(Integer id) {
        return adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(
                String.format("Ad with %d not found", id)
        ));
    }

    private boolean checkAdAuthorId(CustomUserDetails userDetails, Integer adId) {
        UserEntity userEntity = getUserEntity(userDetails);
        return userEntity.getComments().stream()
                .anyMatch(commentEntity -> commentEntity.getUser().getAds().stream()
                        .anyMatch(adEntity -> adEntity.getPk().equals(adId)));
    }

}
