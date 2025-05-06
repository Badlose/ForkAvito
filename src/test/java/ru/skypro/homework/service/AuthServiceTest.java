package ru.skypro.homework.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.NewTypeTesting.TestHelper;
import ru.skypro.homework.dto.accept.Login;
import ru.skypro.homework.dto.accept.Register;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.CustomUserDetailsService;
import ru.skypro.homework.service.impl.AuthServiceImpl;

import static org.assertj.core.api.Assertions.*;
import static ru.skypro.NewTypeTesting.TestHelper.*;

@SpringBootTest
@Transactional
public class AuthServiceTest {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private AuthServiceImpl service;

    @Test
    void shouldRegisterNewUser() {
        Register register = getRegister();

        service.register(register);

        UserEntity userEntity = repository.findByUsername(register.getUsername()).orElseThrow();

        assertThat(new BCryptPasswordEncoder().matches(register.getPassword(), userEntity.getPassword()));
        assertThat(userEntity.getUsername()).isEqualTo(register.getUsername());
        assertThat(userEntity.getFirstName()).isEqualTo(register.getFirstName());
        assertThat(userEntity.getLastName()).isEqualTo(register.getLastName());
        assertThat(userEntity.getPhone()).isEqualTo(register.getPhone());
        assertThat(userEntity.getRole()).isEqualTo(register.getRole());
    }

//    @Test todo
//    void shouldLogin() {
//        Login login = getLogin();
//
//        assertThat(service.login(login)
//
//    }

}
