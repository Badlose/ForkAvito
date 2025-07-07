package ru.skypro.homework.service;

import ru.skypro.homework.dto.accept.Login;
import ru.skypro.homework.dto.accept.Register;

public interface AuthService {

    boolean login(Login login);

    boolean register(Register register);

}
