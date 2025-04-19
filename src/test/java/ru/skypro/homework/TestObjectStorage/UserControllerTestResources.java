package ru.skypro.homework.TestObjectStorage;

import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;

import java.util.UUID;

public abstract class UserControllerTestResources {

    protected NewPassword getTestNewPassword() {
        NewPassword newPassword = new NewPassword();
        newPassword.setCurrentPassword("current password");
        newPassword.setNewPassword("new password");
        return newPassword;
    }

    protected User getTestUser() {
        User user = new User();
        user.setId(1);
        user.setFirstName("user first name");
        user.setLastName("user last name");
        user.setPhone("user phone");
        user.setRole(Role.USER);
        user.setImage("user image");
        return user;
    }

    protected UpdateUser getTestUpdateUser() {
        UpdateUser updateUser = new UpdateUser();
        updateUser.setFirstName("update user first name");
        updateUser.setLastName("update user last name");
        updateUser.setPhone("update user phone");
        return updateUser;
    }

    protected MultipartFile getTestMultipartFile() {
        return Mockito.mock(MultipartFile.class);
    }

}
