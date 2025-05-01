package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.skypro.homework.dto.accept.CreateOrUpdateComment;
import ru.skypro.homework.dto.give.Comment;
import ru.skypro.homework.dto.give.Comments;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {


    @Mapping(target = "user", ignore = true)
    @Mapping(target = "ad", ignore = true)
    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "user.id", target = "author")
    @Mapping(source = "user.image", target = "authorImage")
    @Mapping(source = "user.firstName", target = "authorFirstName")
    void toCommentEntity(UserEntity user, CreateOrUpdateComment comment, @MappingTarget CommentEntity entity);


    void toComment(CommentEntity entity, @MappingTarget Comment comment);

    List<Comment> toComment(List<CommentEntity> entity);

    default Comments toComments(Integer count, List<CommentEntity> commentList) {
        List<Comment> comments = toComment(commentList);
        return new Comments(count, comments);
    }
}
