package ru.skypro.homework.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.TestObjectStorage.UserControllerTestResources;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest extends UserControllerTestResources {
    @Mock
    private UserService service;
    @InjectMocks
    private UserController controller;

    @Test
    void shouldSetPassword() {
        NewPassword newPassword = getTestNewPassword();
        ResponseEntity<?> expected = ResponseEntity.ok().body("Password successfully changed");

        doNothing().when(service).setPassword(newPassword);
        ResponseEntity<?> actual = controller.setPassword(newPassword);

        assertEquals(expected, actual);
        verify(service, times(1)).setPassword(newPassword);
    }

    @Test
    void shouldGetUser() {
        User expectedUser = getTestUser();

        when(service.getUserSelfInfo()).thenReturn(expectedUser);

        User actualUser = controller.getUser().getBody();

        assertEquals(expectedUser, actualUser);
        verify(service, times(1)).getUserSelfInfo();
    }

    @Test
    void shouldUpdateUser() {
        UpdateUser expectedUser = getTestUpdateUser();

        when(service.updateUser(expectedUser)).thenReturn(expectedUser);

        UpdateUser actual = controller.updateUser(expectedUser).getBody();

        assertEquals(expectedUser, actual);
        verify(service, times(1)).updateUser(expectedUser);
    }

    @Test
    void shouldUpdateUserImage() {
        MultipartFile multipartFile = getTestMultipartFile();
        ResponseEntity<?> expected = ResponseEntity.ok("Image successfully updated");
        doNothing().when(service).updateUserImage(multipartFile);

        ResponseEntity<?> actual = controller.updateUserImage(multipartFile);

        assertEquals(expected, actual);
        verify(service, times(1)).updateUserImage(multipartFile);

    }
}
