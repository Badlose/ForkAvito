package ru.skypro.homework.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Override
    public boolean login(Login login) {
        if (!repository.existsByUsername(login.getUsername())) {
            return false;
        }
        CustomUserDetails userDetails = service.loadUserByUsername(login.getUsername());
        return encoder.matches(login.getPassword(), userDetails.getPassword());
    }

    @Override
    @Transactional
    public boolean register(Register register) {
        if (repository.existsByUsername(register.getUsername())) {
            return false;
        }
        register.setPassword(new BCryptPasswordEncoder().encode(register.getPassword()));
        UserEntity entity = AuthMapper.createNewUser(register);
        repository.save(entity);
        return true;
    }

}
