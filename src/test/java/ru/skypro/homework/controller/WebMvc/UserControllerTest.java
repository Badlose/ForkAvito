package ru.skypro.homework.controller.WebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.accept.NewPassword;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.service.UserService;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.skypro.homework.helper.TestHelper.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
public class UserControllerTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate; //для теста коннекшена

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private static UserRepository staticUserRepository;


    @BeforeAll //todo
    static void setUp(@Autowired UserRepository repository) {
        staticUserRepository = repository;
        String password = "zxczxczxc";
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        UserEntity userEntity = createUserEntityBuilder()
                .username("username")
                .password(encodedPassword)
                .build();

        staticUserRepository.save(userEntity);
    }


    @Test
    @Transactional
    void testConnection() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM information_schema.tables", Integer.class);
        System.out.println(staticUserRepository.findByUsername("username"));
    }

    @Test
    void shouldSetPassword() throws Exception {
        UserEntity userEntity = staticUserRepository.findByUsername("username").orElseThrow();
        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
        Authentication authentication = new TestAuthentication(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        NewPassword newPassword = getNewPassword("zxczxczxc", "asdasdasd");

        ResultActions perform = mockMvc.perform(post("/users/set_password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPassword)));

        perform
                .andExpect(status().isOk())
                .andDo(print());
//                .andExpect(result -> { todo почему так не работает?
//                    assertThat(new BCryptPasswordEncoder()
//                            .matches(newPassword.getNewPassword(), userDetails.getPassword())).isTrue();
//                });
        UserEntity updatedUser = staticUserRepository.findByUsername("username").orElseThrow();
        String encodedPwFromDb = updatedUser.getPassword();

        assertThat(encoder.matches(newPassword.getNewPassword(), encodedPwFromDb)).isTrue();
    }

    @Test
    void shouldGetUserSelfInfo() throws Exception {
        UserEntity userEntity = staticUserRepository.findByUsername("username").orElseThrow();
        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
        Authentication authentication = new TestAuthentication(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResultActions perform = mockMvc.perform(get("/users/me")
                .contentType(MediaType.APPLICATION_JSON));

        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(userEntity.getUsername()))
                .andExpect(jsonPath("$.firstName").value(userEntity.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userEntity.getLastName()));
    }

    @Test
    void getUser_UnauthenticatedUser_ReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/users/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldUpdateUser() throws Exception {
        UserEntity userEntity = staticUserRepository.findByUsername("username").orElseThrow();
        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
        Authentication authentication = new TestAuthentication(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UpdateUser updateUser = getUpdateUser();


        ResultActions perform = mockMvc.perform(patch("/users/me")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUser)));

        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(updateUser.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(updateUser.getLastName()))
                .andExpect(jsonPath("$.phone").value(updateUser.getPhone()));
    }

//    @Test
//    void shouldUpdateUserImage() throws Exception {
//        UserEntity userEntity = staticUserRepository.findByUsername("username").orElseThrow();
//        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
//        Authentication authentication = new TestAuthentication(userDetails);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//
//
//        MultipartFile image = getMultipartFileSemiStub();
//
//        MockMultipartFile mockImage = new MockMultipartFile(
//                "image",
//                "test.png",
//                "image/png",
//                "some image data".getBytes()
//        );
//
//        byte[] imageBytes = "some image data".getBytes();
//
//
//
//        ResultActions perform = mockMvc.perform(patch("/users/me/image")
//                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
//                .content(imageBytes));  todo совсем не пошел
//
//        perform
//                .andExpect(status().isOk());
//    }




//
//    @Test
////    @WithMockUser(username = "string3@mail.ru", password = "zxczxczxc", authorities = {"USER"})
//    void shouldSetPassword() throws Exception {
//        NewPassword newPassword = getNewPassword();
//
//        ResultActions perform = mockMvc.perform(post("/users/set_password")
//
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(newPassword)));
//
//        perform
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        //todo польза от сообщениян в body - его можно протестировать
//    }
//
//    @Test
//    void shouldGetUser() {
//
//    }
}
