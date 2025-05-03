package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.accept.NewPassword;
import ru.skypro.homework.dto.give.User;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.service.UserService;

import static ru.skypro.homework.mapper.UserMapper.toUser;
import static ru.skypro.homework.mapper.UserMapper.toUserEntity;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void setPassword(CustomUserDetails userDetails, NewPassword newPassword) {
        UserEntity userEntity = getUserEntity(userDetails);
        String password = new BCryptPasswordEncoder().encode(newPassword.getNewPassword());
        userEntity.setPassword(password);
        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public User getUserSelfInfo(CustomUserDetails userDetails) {
        UserEntity entity = getUserEntity(userDetails);
        return toUser(entity);
    }

    @Override
    @Transactional
    public UpdateUser updateUser(CustomUserDetails userDetails, UpdateUser updateUser) {
        UserEntity entityFromDb = getUserEntity(userDetails);
        entityFromDb = toUserEntity(entityFromDb, updateUser);
        userRepository.save(entityFromDb);
        return updateUser;
    }

    @Override
    @Transactional
    public void updateUserImage(CustomUserDetails userDetails, MultipartFile image) {
        getUserEntity(userDetails);
    }

    @Transactional
    private UserEntity getUserEntity(CustomUserDetails userDetails) {
        String username = userDetails.getUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User %s not found", username)));
    }

}
