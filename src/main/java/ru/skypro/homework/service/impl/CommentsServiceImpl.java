package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.accept.CreateOrUpdateComment;
import ru.skypro.homework.dto.give.Comment;
import ru.skypro.homework.dto.give.Comments;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.service.CommentsService;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final CommentMapper commentMapper;

    @Override
    public Comments getComments(Integer id) {

        AdEntity adFromDB = adRepository.findById(id).orElseThrow(() -> new CommentNotFoundException.AdNotFoundException(
                String.format("Ad with id {} not found", id)
        ));

        List<CommentEntity> comments = adFromDB.getComments();
        Integer count = comments.size();

        return commentMapper.toComments(count, comments);
    }

    @Override
    public Comment addComment(CustomUserDetails userDetails, Integer id, CreateOrUpdateComment comment) {

        String username = userDetails.getUsername();
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found."));

        AdEntity adFromDB = adRepository.findById(id).orElseThrow(() -> new CommentNotFoundException.AdNotFoundException(
                String.format("Ad with id {} not found", id)
        ));

        CommentEntity commentEntity = CommentEntity.builder()
                .user(userEntity)
                .ad(adFromDB)
                .createdAt(Instant.now())
                .build();

        commentMapper.toCommentEntity(userEntity, comment, commentEntity);

        commentRepository.save(commentEntity);

        Comment commentToReturn = new Comment();

        commentMapper.toComment(commentEntity, commentToReturn);

        return commentToReturn;
    }

    @Override
    public void deleteComment(CustomUserDetails userDetails, Integer adId, Integer commentId) {

        String username = userDetails.getUsername();
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found."));

        AdEntity adFromDB = adRepository.findById(adId).orElseThrow(() -> new CommentNotFoundException.AdNotFoundException(
                String.format("Ad with id {} not found", adId)
        ));

        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(
                        String.format("Comment with id {} not found", commentId)
                ));

        commentRepository.delete(commentEntity);
//
//        List<CommentEntity> commentEntityList = adFromDB.getComments();
//
//        commentEntityList.remove(commentEntity);
//
//        adFromDB.setComments(commentEntityList);
//
//        adRepository.save(adFromDB);

    }

    @Override
    public Comment updateComment(CustomUserDetails userDetails, Integer adId, Integer commentId, CreateOrUpdateComment comment) {

        return new Comment();       // not realized yet
    }

}
