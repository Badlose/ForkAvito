package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.accept.NewPassword;
import ru.skypro.homework.dto.give.User;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.AuthMapper;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.security.CustomUserDetailsService;
import ru.skypro.homework.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final AuthMapper authMapper;
    private final CustomUserDetailsService service;

    @Override
    @Transactional
    public void setPassword(CustomUserDetails userDetails, NewPassword newPassword) {

        String username = userDetails.getUsername();

        UserEntity userEntity = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found."));

        String password = newPassword.getNewPassword();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        newPassword.setNewPassword(encoder.encode(password));

        authMapper.toUserEntity(newPassword, userEntity);

        repository.save(userEntity);
    }

    @Override
    @Transactional
    public User getUserSelfInfo(CustomUserDetails userDetails) {

        String username = userDetails.getUsername();

        UserEntity entity = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found."));

        User user = new User();

        mapper.toUser(entity, user);

        return user;
    }

    @Override
    @Transactional
    public UpdateUser updateUser(CustomUserDetails userDetails, UpdateUser updateUser) {

        String username = userDetails.getUsername();

        UserEntity entity = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found."));

        mapper.toUserEntity(updateUser, entity);

        repository.save(entity);

        return updateUser;
    }

    @Override
    @Transactional
    public void updateUserImage(CustomUserDetails userDetails, MultipartFile image) {

        String username = userDetails.getUsername();

        UserEntity entity = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found."));

        CustomUserDetails user = service.loadUserByUsername(username); // вот так же тоже можно было



    }

}
