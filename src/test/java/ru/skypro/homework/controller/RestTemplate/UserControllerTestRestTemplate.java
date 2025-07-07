package ru.skypro.homework.controller.RestTemplate;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.accept.NewPassword;
import ru.skypro.homework.dto.give.User;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;
import static ru.skypro.homework.helper.TestHelper.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class UserControllerTestRestTemplate {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");
    @Autowired
    private PasswordEncoder encoder;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }


    @BeforeEach
    void setUp() {
        UserEntity userEntity = createUserEntityBuilder()
                .username(getPlainUsername())
                .password(getEncodedOldPassword())
                .build();

        userRepository.save(userEntity);
    }

    @AfterEach
    void seekAndDestroy() {
        SecurityContextHolder.clearContext();
        userRepository.deleteAll();
    }

    @Test
    void shouldSetPassword() {
        HttpHeaders headers = getExtractedHeaders();
        NewPassword newPassword = getNewPassword(getOldPlainPassword(), getNewPlainPassword());

        HttpEntity<NewPassword> requestEntity = new HttpEntity<>(newPassword, headers);

        ResponseEntity<?> responseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/users/set_password",
                requestEntity,
                void.class
        );

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);

        UserEntity updatedUser = userRepository.findByUsername(getPlainUsername()).orElseThrow();
        assertThat(encoder.matches(getNewPlainPassword(), updatedUser.getPassword())).isTrue();
    }

    @Test
    void shouldGetUser() {
        HttpHeaders headers = getExtractedHeaders();
        HttpEntity<NewPassword> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<User> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/users/me",
                HttpMethod.GET,
                requestEntity,
                User.class
        );

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);

        UserEntity userEntity = userRepository.findByUsername(getPlainUsername()).orElseThrow();
        User actualUser = responseEntity.getBody();

        assertThat(actualUser.getEmail()).isEqualTo(userEntity.getUsername());
        assertThat(actualUser.getFirstName()).isEqualTo(userEntity.getFirstName());
        assertThat(actualUser.getLastName()).isEqualTo(userEntity.getLastName());
        assertThat(actualUser.getPhone()).isEqualTo(userEntity.getPhone());
        assertThat(actualUser.getRole()).isEqualTo(userEntity.getRole());
    }

    @Test
    void shouldUpdateUser() {
        HttpHeaders headers = getExtractedHeaders();
        UpdateUser updateUser = getUpdateUser();
        HttpEntity<UpdateUser> requestEntity = new HttpEntity<>(updateUser, headers);

        ResponseEntity<UpdateUser> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/users/me",
                HttpMethod.PATCH,
                requestEntity,
                UpdateUser.class
        );

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);

        UserEntity userEntity = userRepository.findByUsername(getPlainUsername()).orElseThrow();
        UpdateUser actualUser = responseEntity.getBody();

        assertThat(actualUser.getFirstName()).isEqualTo(userEntity.getFirstName());
        assertThat(actualUser.getLastName()).isEqualTo(userEntity.getLastName());
        assertThat(actualUser.getPhone()).isEqualTo(userEntity.getPhone());
    }

}
