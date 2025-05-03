//package ru.skypro.homework.service;
//
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import ru.skypro.NewTypeTesting.TestHelper;
//import ru.skypro.homework.dto.accept.NewPassword;
//import ru.skypro.homework.entity.UserEntity;
//import ru.skypro.homework.repository.UserRepository;
//import ru.skypro.homework.security.CustomUserDetails;
//import ru.skypro.homework.service.impl.UserServiceImpl;
//
//import static org.mockito.Mockito.*;
//import static ru.skypro.NewTypeTesting.TestHelper.*;
//import static ru.skypro.NewTypeTesting.TestHelper.getCustomUserDetails;
//import static ru.skypro.NewTypeTesting.TestHelper.getUserEntity;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceTest {
//    @Mock
//    private UserRepository repository;
//    @InjectMocks
//    private UserServiceImpl service;
//
//    @Test
//    void shouldSetPassword() {
//        NewPassword newPassword = getNewPassword();
//        UserEntity userEntity = getFullUserEntity();
//        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
//
//        when(repository.save(userEntity)).thenReturn(userEntity);
//
//        service.setPassword(userDetails, newPassword);
//
//        verify(service, times(1)).setPassword(userDetails, newPassword);
//    }
//}
