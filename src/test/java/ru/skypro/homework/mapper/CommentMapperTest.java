package ru.skypro.homework.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.accept.CreateOrUpdateComment;
import ru.skypro.homework.dto.give.Comment;
import ru.skypro.homework.dto.give.Comments;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.mapper.UserMapperTestResourceStorage.CommentMapperTestResources;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class CommentMapperTest extends CommentMapperTestResources {

    @Autowired
    private CommentMapper mapper;

    @Test
    void shouldCorrectlyMapCommentToCommentEntity() {

        CreateOrUpdateComment updateComment = getTestCreateOrUpdateComment();

        CommentEntity entity = getTestCommentEntity();

        mapper.toCommentEntity(updateComment, entity);

        assertThat(entity).isNotNull();
        assertThat(entity.getText()).isEqualTo(updateComment.getText());
        log.info("shouldCorrectlyMapCommentToCommentEntity CommentEntity " + entity);
    }

    @Test
    void shouldCorrectlyMapListCommentEntityToListComment() {

        CommentEntity entity = getTestCommentEntity();
        List<CommentEntity> entityList = new ArrayList<>(List.of(entity));

        List<Comment> commentsDTO = mapper.toComment(entityList);

        Comment mappedComment = commentsDTO.get(0);

        assertThat(mappedComment).isNotNull();
        assertThat(mappedComment.getAuthor()).isEqualTo(entity.getAuthor());
        assertThat(mappedComment.getAuthorImage()).isEqualTo(entity.getAuthorImage());
        assertThat(mappedComment.getAuthorFirstName()).isEqualTo(entity.getAuthorFirstName());
        assertThat(mappedComment.getCreatedAt()).isEqualTo(entity.getCreatedAt());
        assertThat(mappedComment.getPk()).isEqualTo(entity.getPk());
        assertThat(mappedComment.getText()).isEqualTo(entity.getText());
        log.info("shouldCorrectlyMapListCommentEntityToListComment CommentsDTO " + commentsDTO);
    }

    @Test
    void shouldCorrectlyMapListCommentEntityToComments() {

        Integer count = 1;
        CommentEntity entity = getTestCommentEntity();
        List<CommentEntity> entityList = new ArrayList<>(List.of(entity));

        Comments comments = mapper.toComments(count, entityList);

        Comment mappedComment = comments.getResults().get(0);

        assertThat(comments).isNotNull();
        assertThat(mappedComment.getAuthor()).isEqualTo(entity.getAuthor());
        assertThat(mappedComment.getAuthorImage()).isEqualTo(entity.getAuthorImage());
        assertThat(mappedComment.getAuthorFirstName()).isEqualTo(entity.getAuthorFirstName());
        assertThat(mappedComment.getCreatedAt()).isEqualTo(entity.getCreatedAt());
        assertThat(mappedComment.getPk()).isEqualTo(entity.getPk());
        assertThat(mappedComment.getText()).isEqualTo(entity.getText());
        log.info("shouldCorrectlyMapListCommentEntityToComments Comments " + comments);
    }

}
