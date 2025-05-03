package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.accept.Register;
import ru.skypro.homework.entity.UserEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.skypro.NewTypeTesting.TestHelper.getRegister;
import static ru.skypro.homework.mapper.AuthMapper.createNewUser;

@SpringBootTest
public class AuthMapperTest {

    @Test
    void shouldCorrectlyMapRegisterToUserEntity() {
        Register register = getRegister();

        UserEntity userEntity = createNewUser(register);

        assertThat(userEntity).isNotNull();
        assertThat(userEntity.getUsername()).isEqualTo(register.getUsername());
        assertThat(userEntity.getPassword()).isEqualTo(register.getPassword());
        assertThat(userEntity.getFirstName()).isEqualTo(register.getFirstName());
        assertThat(userEntity.getLastName()).isEqualTo(register.getLastName());
        assertThat(userEntity.getPhone()).isEqualTo(register.getPhone());
        assertThat(userEntity.getRole()).isEqualTo(register.getRole());
    }

}
