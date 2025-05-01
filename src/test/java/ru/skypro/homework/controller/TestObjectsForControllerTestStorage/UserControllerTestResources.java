//package ru.skypro.homework.controller.TestObjectsForControllerTestStorage;
//
//import org.mockito.Mockito;
//import org.springframework.web.multipart.MultipartFile;
//import ru.skypro.homework.dto.accept.NewPassword;
//import ru.skypro.homework.dto.Role;
//import ru.skypro.homework.dto.UpdateUser;
//import ru.skypro.homework.dto.give.User;
//
//public abstract class UserControllerTestResources {
//
//    protected NewPassword getTestNewPassword() {
//        String currentPassword = "current password";
//        String newPassword = "new password";
//        return new NewPassword(currentPassword, newPassword);
//    }
//
//    protected User getTestUser() {
//        Integer id = 1;
//        String userEmail = "user email";
//        String userFirstName = "user first name";
//        String userLastName = "user last name";
//        String userPhone = "user phone";
//        Role role = Role.USER;
//        String userImage = "user image";
//        return new User(id, userEmail, userFirstName, userLastName, userPhone, role, userImage);
//    }
//
//    protected UpdateUser getTestUpdateUser() {
//        String userFirstName = "user update first name";
//        String userLastName = "user update last name";
//        String userPhone = "user update phone";
//        return new UpdateUser(userFirstName, userLastName, userPhone);
//    }
//
//    protected MultipartFile getTestMultipartFile() {
//        return Mockito.mock(MultipartFile.class);
//    }
//
//}
