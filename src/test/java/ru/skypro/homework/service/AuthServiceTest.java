package ru.skypro.homework.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.accept.Register;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.AuthServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.skypro.homework.helper.TestHelper.getRegister;

@SpringBootTest
@Transactional
public class AuthServiceTest {

    @Autowired
    private UserRepository repository;
    @Autowired
    private AuthServiceImpl service;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    void shouldRegisterNewUser() {
        Register register = getRegister();

        service.register(register);

        UserEntity userEntity = repository.findByUsername(register.getUsername()).orElseThrow();

        assertThat(encoder.matches(register.getPassword(), userEntity.getPassword()));
        assertThat(userEntity.getUsername()).isEqualTo(register.getUsername());
        assertThat(userEntity.getFirstName()).isEqualTo(register.getFirstName());
        assertThat(userEntity.getLastName()).isEqualTo(register.getLastName());
        assertThat(userEntity.getPhone()).isEqualTo(register.getPhone());
        assertThat(userEntity.getRole()).isEqualTo(register.getRole());
    }

}
