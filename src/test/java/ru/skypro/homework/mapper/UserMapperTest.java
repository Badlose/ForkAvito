package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.give.User;
import ru.skypro.homework.entity.UserEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.skypro.homework.helper.TestHelper.getUpdateUser;
import static ru.skypro.homework.helper.TestHelper.getUserEntity;

@SpringBootTest
public class UserMapperTest {

    @Test
    void shouldCorrectlyMapUserEntityToUser() {
        UserEntity entity = getUserEntity();

        User userDTO = UserMapper.toUser(entity);

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getEmail()).isEqualTo(entity.getUsername());
        assertThat(userDTO.getFirstName()).isEqualTo(entity.getFirstName());
        assertThat(userDTO.getLastName()).isEqualTo(entity.getLastName());
        assertThat(userDTO.getPhone()).isEqualTo(entity.getPhone());
        assertThat(userDTO.getRole()).isEqualTo(entity.getRole());
        assertThat(userDTO.getImage()).isEqualTo(entity.getImage());
    }

    @Test
    void shouldCorrectlyMapUpdateUserToUserEntity() {
        UpdateUser updateUser = getUpdateUser();
        UserEntity userEntity = getUserEntity();

        UserEntity mappedEntity = UserMapper.toUserEntity(userEntity, updateUser);

        assertThat(mappedEntity).isNotNull();
        assertThat(mappedEntity.getFirstName()).isEqualTo(updateUser.getFirstName());
        assertThat(mappedEntity.getLastName()).isEqualTo(updateUser.getLastName());
        assertThat(mappedEntity.getPhone()).isEqualTo(updateUser.getPhone());
    }

}
