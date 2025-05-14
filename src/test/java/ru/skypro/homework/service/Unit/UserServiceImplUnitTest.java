package ru.skypro.homework.service.Unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.trilead.ssh2.crypto.PEMDecoder;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.accept.NewPassword;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.skypro.homework.helper.TestHelper.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplUnitTest {
    @Mock
    private UserRepository repository;
    @Mock
    private PasswordEncoder encoder;
    @InjectMocks
    private UserServiceImpl service;

    @Test
    @Transactional
    void shouldSetPassword() {
        UserEntity userEntity = createUserEntity();
        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
        NewPassword newPassword = getNewPassword();

        when(repository.save(userEntity)).thenReturn(userEntity);
        when(repository.findByUsername(userEntity.getUsername())).thenReturn(Optional.of(userEntity));
        when(encoder.encode(newPassword.getNewPassword())).thenReturn(getEncodedNewPassword());

        service.setPassword(userDetails, newPassword);

        verify(repository, times(1)).save(userEntity);
    }

    @Test
    void shouldGetUserSelfInfo() {
        UserEntity userEntity = getUserEntity();
        CustomUserDetails userDetails = new CustomUserDetails(userEntity);

        when(repository.findByUsername(userEntity.getUsername())).thenReturn(Optional.of(userEntity));

        service.getUserSelfInfo(userDetails);

        verify(repository, times(1)).findByUsername(userEntity.getUsername());
    }

    @Test
    void shouldUpdateUser() {
        UserEntity userEntity = getUserEntity();
        String username = userEntity.getUsername();
        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
        UpdateUser updateUser = getUpdateUser();
        userEntity = UserMapper.toUserEntity(userEntity, updateUser);

        System.out.println(userEntity);

        when(repository.save(userEntity)).thenReturn(userEntity);
        when(repository.findByUsername(userEntity.getUsername())).thenReturn(Optional.of(userEntity));

        service.updateUser(userDetails, updateUser);

        assertThat(userEntity.getFirstName()).isEqualTo(updateUser.getFirstName());
        assertThat(userEntity.getLastName()).isEqualTo(updateUser.getLastName());
        assertThat(userEntity.getPhone()).isEqualTo(updateUser.getPhone());
        verify(repository, times(1)).save(userEntity);
        verify(repository, times(1)).findByUsername(userEntity.getUsername());
    }
}

