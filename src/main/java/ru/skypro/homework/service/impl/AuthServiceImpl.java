package ru.skypro.homework.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
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
    private final AuthMapper mapper;
//    private final JdbcUserDetailsManager manager;
    private final CustomUserDetailsService service;
    private final AuthenticationManager manager;

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
//        service.loadUserByUsername(register.getUsername());

//        manager.createUser(
//                CustomUserDetails.builder()
//                        .username(register.getUsername())
//                        .password(encoder.encode(register.getPassword()))
//                        .firstName(register.getFirstName())
//                        .lastName(register.getLastName())
//                        .phone(register.getPhone())
//                        .role(register.getRole().name())
//                        .build());

//        CustomUserDetails user = new CustomUserDetails(register.getUsername(), register.getPassword(), register.getFirstName(), register.getLastName(), register.getPhone(),
//                register.getRole().toString());

        register.setPassword(new BCryptPasswordEncoder().encode(register.getPassword()));

        UserEntity entity = AuthMapper.createNewUser(register);

        repository.save(entity);

        return true;
    }
//    @Override
//    public boolean login(Login login) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        login.getUsername(),
//                        login.getPassword()
//                )
//        );
//        try {
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            return true;
//        } catch (Exception e) {
//            log.error("Login failed: {}", e.getMessage());
//        }
//        return false;
//    }
//
//    @Override
//    @Transactional
//    public boolean register(Register register) {
//        if (repository.existsByUsername(register.getUsername())) {
//            throw new UserAlreadyExistException(String.format(
//                    "User with %s username already exist", register.getUsername()
//            ));
//        }
//
//        UserEntity newUser = new UserEntity();
//        mapper.toUserEntity(register, newUser);
//
//        return newUser != null && !newUser.getPassword().isBlank();     //кривовато слегка. надо ли ещё проверки. нужны ли они вовсе?

//    }

}
