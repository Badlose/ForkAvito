package ru.skypro.homework.service.Unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.NewTypeTesting.TestHelper;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.accept.Login;
import ru.skypro.homework.dto.accept.Register;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.AuthMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.security.CustomUserDetailsService;
import ru.skypro.homework.service.impl.AdsServiceImpl;
import ru.skypro.homework.service.impl.AuthServiceImpl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.skypro.NewTypeTesting.TestHelper.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceUnitTest {
    @Mock
    private UserRepository repository;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private CustomUserDetailsService userDetailsService;
    @InjectMocks
    private AuthServiceImpl service;

    @Test
    void shouldRegister() { //todo password encoder не консистентный?
        Register register = getRegister();
        register.setPassword(new BCryptPasswordEncoder().encode(register.getPassword()));

        UserEntity userEntity = AuthMapper.createNewUser(register);

        when(repository.existsByUsername(register.getUsername())).thenReturn(false);
        when(repository.save(ArgumentMatchers.any(UserEntity.class))).thenReturn(userEntity);

        service.register(register);

        verify(repository, times(1)).existsByUsername(register.getUsername());
        verify(repository, times(1)).save(ArgumentMatchers.any(UserEntity.class));
        assertThat(register.getUsername()).isEqualTo(userEntity.getUsername());
    }

    @Test
    void shouldLogin() {
        Login login = getLogin();
        CustomUserDetails userDetails = getCustomUserDetails();


        when(repository.existsByUsername(login.getUsername())).thenReturn(true);
        when(userDetailsService.loadUserByUsername(login.getUsername())).thenReturn(userDetails);
        when(encoder.matches(login.getPassword(), userDetails.getPassword())).thenReturn(true);

        service.login(login);

        verify(repository, times(1)).existsByUsername(login.getUsername());
        verify(userDetailsService, times(1)).loadUserByUsername(login.getUsername());
        verify(encoder, times(1)).matches(login.getPassword(), userDetails.getPassword());
    }
}
