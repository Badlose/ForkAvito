package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.skypro.homework.dto.accept.CreateOrUpdateComment;
import ru.skypro.homework.dto.give.Comment;
import ru.skypro.homework.dto.give.Comments;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

//    @Mapping(target = "user", ignore = true)
//    @Mapping(target = "ad", ignore = true)
//    @Mapping(target = "pk", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(source = "user.image", target = "authorImage")
//    @Mapping(source = "user.firstName", target = "authorFirstName")
//    CommentEntity toCommentEntity(UserEntity user, CreateOrUpdateComment comment);

    static CommentEntity createComment(UserEntity userEntity, AdEntity adFromDB, CreateOrUpdateComment comment) {
        return CommentEntity.builder()
                .authorImage(userEntity.getImage())
                .authorFirstName(userEntity.getFirstName())
                .createdAt(Instant.now())
                .text(comment.getText())
                .user(userEntity)
                .ad(adFromDB)
                .build();
    }

//    Comment toComment(CommentEntity entity);

    static Comment toComment(CommentEntity commentEntity) {
        return Comment.builder()
                .author(commentEntity.getUser().getId())
                .authorImage(commentEntity.getUser().getImage())
                .authorFirstName(commentEntity.getUser().getFirstName())
                .createdAt(commentEntity.getCreatedAt())
                .pk(commentEntity.getPk())
                .text(commentEntity.getText())
                .build();
    }


    static Comments toComments(List<CommentEntity> commentList) {
        List<Comment> comments = new ArrayList<>();
        for (CommentEntity commentEntity : commentList) {
            comments.add(toComment(commentEntity));
        }
        return new Comments(comments.size(), comments);
    }
}
