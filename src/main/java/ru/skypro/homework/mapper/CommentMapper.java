package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.skypro.homework.dto.accept.CreateOrUpdateComment;
import ru.skypro.homework.dto.give.Comment;
import ru.skypro.homework.dto.give.Comments;
import ru.skypro.homework.entity.CommentEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "authorImage", ignore = true)
    @Mapping(target = "authorFirstName", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "pk", ignore = true)
    void toCommentEntity(CreateOrUpdateComment comment, @MappingTarget CommentEntity entity);

    List<Comment> toComment(List<CommentEntity> entity);

    default Comments toComments(Integer count, List<CommentEntity> commentList) {
        List<Comment> comments = toComment(commentList);
        return new Comments(count, comments);
    }
}
