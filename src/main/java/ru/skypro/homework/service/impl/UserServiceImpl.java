package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.accept.NewPassword;
import ru.skypro.homework.dto.give.Ads;
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

    /**
     * Set new user`s password
     * @param userDetails Authorized user from CustomUserDetails
     * @param newPassword {@link NewPassword} DTO with current and new passwords
     */
    @Override
    @Transactional
    public void setPassword(CustomUserDetails userDetails, NewPassword newPassword) {
        UserEntity userEntity = getUserEntity(userDetails);
        String password = passwordEncoder.encode(newPassword.getNewPassword());
        userEntity.setPassword(password);
        userRepository.save(userEntity);
        log.info("New user was created with username {}", userEntity.getUsername());
    }

    /**
     * Get user`s information
     * @param userDetails Authorized user from CustomUserDetails
     * @return {@link User}
     */
    @Override
    @Transactional
    public User getUserSelfInfo(CustomUserDetails userDetails) {
        UserEntity entity = getUserEntity(userDetails);
        return toUser(entity);
    }

    /**
     * Update user`s information
     * @param userDetails Authorized user from CustomUserDetails
     * @param updateUser {@link UpdateUser} user`s new data
     * @return {@link UpdateUser}
     */
    @Override
    @Transactional
    public UpdateUser updateUser(CustomUserDetails userDetails, UpdateUser updateUser) {
        UserEntity entityFromDb = getUserEntity(userDetails);
        entityFromDb = toUserEntity(entityFromDb, updateUser);
        userRepository.save(entityFromDb);
        log.info("User with id {} was updated", entityFromDb.getId());
        return updateUser;
    }

    /**
     * Update user`s image
     * @param userDetails Authorized user from CustomUserDetails
     * @param image new image
     */
    @Override
    @Transactional
    public void updateUserImage(CustomUserDetails userDetails, MultipartFile image) {
        UserEntity userEntity = getUserEntity(userDetails);
        Integer userId = userEntity.getId();
        deleteUserImage(userEntity);
        String imageUrl = imageService.uploadUserImage(image, userId);
        log.info("Image for user with id {} was updated", userId);
        userEntity.setImage(imageUrl);
        userRepository.save(userEntity);
    }

    /**
     * Get user`s image
     * @param id image id
     * @return image byte[]
     */
    @Override
    @Transactional
    public byte[] getUserImage(String id) {
        return imageService.getUsersImageBytes(id);
    }

    /**
     * Get User entity from DB
     * @param userDetails Authorized user from CustomUserDetails
     * @return {@link UserEntity}
     */
    private UserEntity getUserEntity(CustomUserDetails userDetails) {
        String username = userDetails.getUsername();
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    /**
     * Remove user`s image from DB
     * @param entity user entity from DB
     */
    private void deleteUserImage(UserEntity entity) {
        if (entity.getImage() != null) {
            imageService.deleteImage(entity.getImage());
        }
    }
}
