package ru.skypro.homework.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.skypro.homework.controller.TestObjectsForControllerTestStorage.AuthControllerTestResources;
import ru.skypro.homework.dto.accept.Login;
import ru.skypro.homework.dto.accept.Register;
import ru.skypro.homework.service.AuthService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest extends AuthControllerTestResources {
    @Mock
    private AuthService service;
    @InjectMocks
    private AuthController controller;

    //TODO
//    @Test
//    void shouldLogin() {
//        Login login = getTestLogin();
//        String username = login.getUsername();
//        String password = login.getPassword();
//        ResponseEntity<?> expected = ResponseEntity.ok().build();
//
//        when(service.login(username, password)).thenReturn(true);
//
//        ResponseEntity<?> actual = controller.login(login);
//
//        assertEquals(expected, actual);
//        verify(service, times(1)).login(username, password);
//    }

    @Test
    void shouldRegister() {
        Register register = getTestRegister();
        HttpStatus expected = HttpStatus.CREATED;

        when(service.register(register)).thenReturn(true);

        HttpStatus actual = controller.register(register).getStatusCode();

        assertEquals(expected, actual);
        verify(service, times(1)).register(register);
    }
}
