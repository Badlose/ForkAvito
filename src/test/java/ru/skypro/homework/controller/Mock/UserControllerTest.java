package ru.skypro.homework.controller.Mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.skypro.homework.controller.UserController;
import ru.skypro.homework.dto.accept.NewPassword;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.service.UserService;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.skypro.NewTypeTesting.TestHelper.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "string3@mail.ru", password = "zxczxczxc", authorities = {"USER"})
public class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService service;

    @Test
//    @WithMockUser(username = "string3@mail.ru", password = "zxczxczxc", authorities = {"USER"})
    void shouldSetPassword() throws Exception {
        NewPassword newPassword = getNewPassword();

        ResultActions perform = mockMvc.perform(post("/users/set_password")

                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPassword)));

        perform
                .andExpect(status().isOk())
                .andDo(print());

        //todo полбза от сообщениян в body - его можно протестировать
    }

    @Test
    void shouldGetUser() {

    }
}
