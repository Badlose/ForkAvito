package ru.skypro.homework.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.accept.NewPassword;
import ru.skypro.homework.dto.accept.Register;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapperTestResourceStorage.AuthMapperTestResources;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class AuthMapperTest extends AuthMapperTestResources {
    @Autowired
    private AuthMapper mapper;

    @Test
    void shouldCorrectlyMapNewPasswordToUserEntity() {
        NewPassword newPassword = getTestNewPassword();
        UserEntity userEntity = getTestUserEntity();

        mapper.toUserEntity(newPassword, userEntity);

        assertThat(userEntity).isNotNull();
        assertThat(userEntity.getPassword()).isEqualTo(newPassword.getNewPassword());

        log.info("shouldCorrectlyMapNewPasswordToUserEntity UserEntity newPassword" + userEntity.getPassword());
    }

    @Test
    void shouldCorrectlyMapRegisterToUserEntity() {
        Register register = getTestRegister();
        UserEntity userEntity = getTestUserEntity();

        mapper.toUserEntity(register, userEntity);

        assertThat(userEntity).isNotNull();
        assertThat(userEntity.getUsername()).isEqualTo(register.getUsername());
        assertThat(userEntity.getPassword()).isEqualTo(register.getPassword());
        assertThat(userEntity.getFirstName()).isEqualTo(register.getFirstName());
        assertThat(userEntity.getLastName()).isEqualTo(register.getLastName());
        assertThat(userEntity.getPhone()).isEqualTo(register.getPhone());
        assertThat(userEntity.getRole()).isEqualTo(register.getRole());

        log.info("shouldCorrectlyMapRegisterToUserEntity UserEntity" + userEntity);
    }

}
