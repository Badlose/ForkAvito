package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.accept.CreateOrUpdateComment;
import ru.skypro.homework.dto.give.Comment;
import ru.skypro.homework.dto.give.Comments;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.skypro.NewTypeTesting.TestHelper.*;
import static ru.skypro.homework.mapper.CommentMapper.createComment;
import static ru.skypro.homework.mapper.CommentMapper.toComments;

@SpringBootTest
public class CommentMapperTest {

    @Test
    void shouldCorrectlyMapCommentToCommentEntity() {
        UserEntity user = getFullUserEntity();
        AdEntity adEntity = getFullAdEntity();
        CreateOrUpdateComment comment = getCreateOrUpdateComment();

        CommentEntity entity = createComment(user, adEntity, comment);

        assertThat(entity).isNotNull();
        assertThat(entity.getText()).isEqualTo(comment.getText());
    }


    @Test
    void shouldCorrectlyMapListCommentEntityToComments() {
        List<CommentEntity> entityList = List.of(getFullCommentEntity());

        Comments comments = toComments(entityList);

        Comment mappedComment = comments.getResults().get(0);
        CommentEntity entity = entityList.get(0);

        assertThat(comments).isNotNull();
        assertThat(mappedComment.getAuthor()).isEqualTo(entity.getUser().getId());
        assertThat(mappedComment.getCreatedAt()).isEqualTo(entity.getCreatedAt());
        assertThat(mappedComment.getPk()).isEqualTo(entity.getPk());
        assertThat(mappedComment.getText()).isEqualTo(entity.getText());
    }

}
