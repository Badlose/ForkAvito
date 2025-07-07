package ru.skypro.homework.service.Unit;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.homework.dto.accept.Register;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.AuthMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.CustomUserDetailsService;
import ru.skypro.homework.service.impl.AuthServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static ru.skypro.homework.helper.TestHelper.getEncodedNewPassword;
import static ru.skypro.homework.helper.TestHelper.getRegister;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class AuthServiceUnitTest {
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private UserRepository repository;
    @InjectMocks
    private AuthServiceImpl service;

    @Test
    void shouldRegister() {
        Register register = getRegister();

        UserEntity userEntity = AuthMapper.createNewUser(register);

        when(repository.existsByUsername(register.getUsername())).thenReturn(false);
        when(repository.save(ArgumentMatchers.any(UserEntity.class))).thenReturn(userEntity);
        when(encoder.encode(register.getPassword())).thenReturn(getEncodedNewPassword());

        service.register(register);

        verify(repository, times(1)).existsByUsername(register.getUsername());
        verify(repository, times(1)).save(ArgumentMatchers.any(UserEntity.class));
        assertThat(register.getUsername()).isEqualTo(userEntity.getUsername());
    }

}
