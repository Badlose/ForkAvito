package ru.skypro.homework.TestObjectStorage;

import ru.skypro.homework.dto.Login;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.Role;

public abstract class AuthControllerTestResources {

    protected Login getTestLogin() {
        Login login = new Login();
        login.setUsername("login username");
        login.setPassword("login password");
        return login;
    }

    protected Register getTestRegister() {
        Register register = new Register();
        register.setUsername("register username");
        register.setPassword("register password");
        register.setFirstName("register first name");
        register.setLastName("register last name");
        register.setPhone("register phone");
        register.setRole(Role.USER);
        return register;
    }
}
