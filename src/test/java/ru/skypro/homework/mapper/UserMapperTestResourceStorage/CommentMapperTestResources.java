//package ru.skypro.homework.mapper.UserMapperTestResourceStorage;
//
//import ru.skypro.homework.dto.accept.CreateOrUpdateComment;
//import ru.skypro.homework.entity.AdEntity;
//import ru.skypro.homework.entity.CommentEntity;
//import ru.skypro.homework.entity.UserEntity;
//
//import static org.mockito.Mockito.mock;
//
//public abstract class CommentMapperTestResources {
//
//    protected CreateOrUpdateComment getTestCreateOrUpdateComment() {
//        String commentText = "created or updated comment text";
//        return new CreateOrUpdateComment(commentText);
//    }
//
//    protected CommentEntity getTestCommentEntity() {
//        Integer author = 1;
//        String authorImage = "image";
//        String authorFirstNAme = "name";
//        Long createdAt = 123123L;
//        Integer pk = 1;
//        String text = "text";
//        UserEntity user = mock(UserEntity.class);
//        AdEntity ad = mock(AdEntity.class);
//
//        return new CommentEntity(author, authorImage, authorFirstNAme, createdAt, pk, text, user, ad);
//    }
//
//}
