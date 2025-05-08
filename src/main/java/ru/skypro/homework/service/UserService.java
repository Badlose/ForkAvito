package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.accept.NewPassword;
import ru.skypro.homework.dto.give.User;
import ru.skypro.homework.security.CustomUserDetails;

import java.io.IOException;

public interface UserService {

    void setPassword(CustomUserDetails userDetails, NewPassword newPassword);

    User getUserSelfInfo(CustomUserDetails userDetails);

    UpdateUser updateUser(CustomUserDetails userDetails, UpdateUser updateUser);

    void updateUserImage(CustomUserDetails userDetails, MultipartFile image) throws IOException;

    byte[] getUserImage(String id);

//    String uploadImage(CustomUserDetails userDetails, MultipartFile image);
}
