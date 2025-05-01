//package ru.skypro.homework.mapper;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.skypro.homework.dto.UpdateUser;
//import ru.skypro.homework.dto.give.User;
//import ru.skypro.homework.entity.UserEntity;
//import ru.skypro.homework.mapper.UserMapperTestResourceStorage.UserMapperTestResources;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Slf4j
//public class UserMapperTest extends UserMapperTestResources {
//
//    @Autowired
//    private UserMapper mapper;
//
//    @Test
//    void shouldCorrectlyMapUserEntityToUser() {
//        UserEntity entity = getTestUserEntity();
//
//        User userDTO = getTestUser();
//
//        mapper.toUser(entity, userDTO);
//
//        assertThat(userDTO).isNotNull();
//        assertThat(userDTO.getEmail()).isEqualTo(entity.getUsername());
//        assertThat(userDTO.getFirstName()).isEqualTo(entity.getFirstName());
//        assertThat(userDTO.getLastName()).isEqualTo(entity.getLastName());
//        assertThat(userDTO.getPhone()).isEqualTo(entity.getPhone());
//        assertThat(userDTO.getRole()).isEqualTo(entity.getRole());
//        assertThat(userDTO.getImage()).isEqualTo(entity.getImage());
//
//        log.info("shouldCorrectlyMapUserEntityToUser userDTO" + userDTO);
//    }
//
//    @Test
//    void shouldCorrectlyMapUpdateUserToUserEntity() {
//
//        UpdateUser updateUser = getTestUpdateUser();
//
//        UserEntity entity = getTestUserEntity();
//
//        mapper.toUserEntity(updateUser, entity);
//
//        assertThat(entity).isNotNull();
//        assertThat(entity.getFirstName()).isEqualTo(updateUser.getFirstName());
//        assertThat(entity.getLastName()).isEqualTo(updateUser.getLastName());
//        assertThat(entity.getPhone()).isEqualTo(updateUser.getPhone());
//
//        log.info("shouldCorrectlyMapUpdateUserToUserEntity entity " + entity);
//    }
//
//}
