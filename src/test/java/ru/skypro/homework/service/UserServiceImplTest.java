package ru.skypro.homework.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.accept.NewPassword;
import ru.skypro.homework.dto.give.User;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.service.impl.UserServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.skypro.NewTypeTesting.TestHelper.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceImplTest {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserServiceImpl service;

    @Test
    void shouldSetPassword() {
        UserEntity userEntity = createUserEntityBuilder()
                .id(null)
                .build();
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
        NewPassword newPassword = getNewPassword();


        repository.save(userEntity);
        service.setPassword(customUserDetails, newPassword);

        UserEntity userFromDb = repository.findByUsername(userEntity.getUsername()).orElseThrow();
        assertThat(new BCryptPasswordEncoder().matches(newPassword.getNewPassword(), userFromDb.getPassword()));
    }

    @Test
    void shouldGetUserSelfInfo() {
        UserEntity userEntity = createUserEntityBuilder()
                .id(null)
                .build();
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
        repository.save(userEntity);

        User userFromService = service.getUserSelfInfo(customUserDetails);

        assertThat(userFromService.getId()).isEqualTo(userEntity.getId());
        assertThat(userFromService.getEmail()).isEqualTo(userEntity.getUsername());
        assertThat(userFromService.getFirstName()).isEqualTo(userEntity.getFirstName());
        assertThat(userFromService.getLastName()).isEqualTo(userEntity.getLastName());
        assertThat(userFromService.getPhone()).isEqualTo(userEntity.getPhone());
        assertThat(userFromService.getRole()).isEqualTo(userEntity.getRole());
        assertThat(userFromService.getImage()).isEqualTo(userEntity.getImage());
    }

    @Test
    void shouldUpdateUser() {
        UserEntity userEntity = createUserEntityBuilder()
                .id(null)
                .build();
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
        repository.save(userEntity);
        UpdateUser updateUser = getUpdateUser();

        service.updateUser(customUserDetails, updateUser);

        UserEntity userEntityFromDb = repository.findByUsername(userEntity.getUsername()).orElseThrow();

        assertThat(userEntityFromDb.getFirstName()).isEqualTo(updateUser.getFirstName());
        assertThat(userEntityFromDb.getLastName()).isEqualTo(updateUser.getLastName());
        assertThat(userEntityFromDb.getPhone()).isEqualTo(updateUser.getPhone());
    }

}
