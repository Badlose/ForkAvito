package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

import static ru.skypro.homework.mapper.UserMapper.toUser;
import static ru.skypro.homework.mapper.UserMapper.toUserEntity;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final ImageService imageService;

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
    public void updateUserImage(CustomUserDetails userDetails, MultipartFile image) throws IOException {
        UserEntity userEntity = getUserEntity(userDetails);
        String imageUrl = "\\" + imageService.uploadUserImage(image, userEntity.getId());
//        String trimmedImageUrl = "/users/images/" + imageUrl;
//        String trimmedImageUrl = "/users/images" + imageUrl.substring(0, imageUrl.lastIndexOf(".") - 1);
        userEntity.setImage(imageUrl);
        log.info("USERENTITY IMAGE             " + userEntity.getImage());

        userRepository.save(userEntity);
    }

//    @Override
//    public String uploadImage(CustomUserDetails userDetails, MultipartFile image) {
//        service.uploadImage(userDetails, image);
//    }

    @Override
    @Transactional
    public byte[] getUserImage(String id) {
        return imageService.getUsersImageBytes(id);
    }
    @Transactional
    private UserEntity getUserEntity(CustomUserDetails userDetails) {
        String username = userDetails.getUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User %s not found", username)));
    }

}
