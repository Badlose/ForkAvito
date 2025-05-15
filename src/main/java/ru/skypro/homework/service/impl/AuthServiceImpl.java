package ru.skypro.homework.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.accept.Login;
import ru.skypro.homework.dto.accept.Register;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.AuthMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.security.CustomUserDetailsService;
import ru.skypro.homework.service.AuthService;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final CustomUserDetailsService service;

    /**
     * Login user by auth data
     * @param login DTO object from
     * @return {@code true} if user with this {@code userName} is existed and
     * {@code password} is correct, <br>
     * {@code false} otherwise
     */
    @Override
    public boolean login(Login login) {
        if (!repository.existsByUsername(login.getUsername())) {
            return false;
        }
        CustomUserDetails userDetails = service.loadUserByUsername(login.getUsername());
        return encoder.matches(login.getPassword(), userDetails.getPassword());
    }

    /**
     * Register new user
     * @param register object with new user's data
     * @return {@code true} if new user successfully registered, <br>
     * {@code false} if user with this username is already exist
     */
    @Override
    @Transactional
    public boolean register(Register register) {
        if (repository.existsByUsername(register.getUsername())) {
            return false;
        }
        register.setPassword(encoder.encode(register.getPassword()));
        UserEntity entity = AuthMapper.createNewUser(register);
        repository.save(entity);
        return true;
    }

}
