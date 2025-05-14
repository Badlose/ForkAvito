package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import static ru.skypro.homework.mapper.UserMapper.toUser;
import static ru.skypro.homework.mapper.UserMapper.toUserEntity;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void setPassword(CustomUserDetails userDetails, NewPassword newPassword) {
        UserEntity userEntity = getUserEntity(userDetails);
        String password = passwordEncoder.encode(newPassword.getNewPassword());
        userEntity.setPassword(password);
        userRepository.save(userEntity);
        log.info("New user was created with username {}", userEntity.getUsername());
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
        log.info("User with id {} was updated", entityFromDb.getId());
        return updateUser;
    }

    @Override
    @Transactional
    public void updateUserImage(CustomUserDetails userDetails, MultipartFile image) {
        UserEntity userEntity = getUserEntity(userDetails);
        Integer userId = userEntity.getId();
        String imageUrl = imageService.uploadUserImage(image, userId);
        log.info("Image for user with id {} was updated", userId);
        userEntity.setImage(imageUrl);
        userRepository.save(userEntity);
    }


    @Override
    @Transactional
    public byte[] getUserImage(String id) {
        return imageService.getUsersImageBytes(id);
    }

    private UserEntity getUserEntity(CustomUserDetails userDetails) {
        String username = userDetails.getUsername();
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

}
