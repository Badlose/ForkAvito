//package ru.skypro.homework.controller;
//
//import org.apache.tomcat.util.buf.UEncoder;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//import ru.skypro.NewTypeTesting.TestHelper;
//import ru.skypro.homework.entity.UserEntity;
//import ru.skypro.homework.repository.UserRepository;
//import ru.skypro.homework.security.CustomUserDetails;
//import ru.skypro.homework.service.UserService;
//
//import static org.assertj.core.api.Assertions.*;
//import static ru.skypro.NewTypeTesting.TestHelper.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
////@ActiveProfiles("test")
//public class UserControllerTestRestTemplate {
//
//    @LocalServerPort
//    private int port;
//    @Autowired
//    private TestRestTemplate restTemplate;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private UserService service;
////    private static final String userUsername = "string3@mail.ru";
////    private static final String userPassword = "$2a$10$kDgik.v7HANSUqm73LIg8.l.imwvICUG058cido5sUnBgoJtQUTXe";
//
//
//    @BeforeEach
//    void setUp() {
//        UserEntity userEntity = createUserEntityHashPassword();
//        userRepository.save(userEntity);
//    }
//
//    @Test
//    @WithMockUser(username = "user", password = "zxczxczxc")
//    void shouldGetUser() {
//
//        ResponseEntity<?> userFromDb = restTemplate
//                .getForEntity(
//                "http://localhost:" + port + "/users/me", UserEntity.class
//        );
//
//        assertThat(userFromDb).isNotNull();
//        assertThat(userFromDb.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }
//}
