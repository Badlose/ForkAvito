package ru.skypro.homework.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.accept.Login;
import ru.skypro.homework.dto.accept.Register;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exception.UserAlreadyExistException;
import ru.skypro.homework.mapper.AuthMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final AuthMapper mapper;
    private final AuthenticationManager authenticationManager;

    @Override
    public boolean login(Login login) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername(),
                        login.getPassword()
                )
        );
        try {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } catch (Exception e) {
            log.error("Login failed: {}", e.getMessage());
        }
        return false;
    }

    @Override
    @Transactional
    public boolean register(Register register) {
        if (repository.existsByUsername(register.getUsername())) {
            throw new UserAlreadyExistException(String.format(
                    "User with %s username already exist", register.getUsername()
            ));
        }

        UserEntity newUser = new UserEntity();
        mapper.toUserEntity(register, newUser);

        return newUser != null && !newUser.getPassword().isBlank();     //кривовато слегка. надо ли ещё проверки. нужны ли они вовсе?
    }

//    @Override
//    public boolean login(String userName, String password) {
//        if (!manager.userExists(userName)) {
//            return false;
//        }
//        UserDetails userDetails = manager.loadUserByUsername(userName);
//        return encoder.matches(password, userDetails.getPassword());
//    }
//
//    @Override
//    public boolean register(Register register) {
//        if (manager.userExists(register.getUsername())) {
//            return false;
//        }
//        manager.createUser(
//                User.builder()
//                        .passwordEncoder(this.encoder::encode)
//                        .password(register.getPassword())
//                        .username(register.getUsername())
//                        .roles(register.getRole().name())
//                        .build());
//        return true;
//    }

}
