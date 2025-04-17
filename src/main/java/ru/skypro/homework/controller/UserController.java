package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody SetPasswordDTO spDTO) {
        if (userService.setPassword(spDTO)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUser() {

    }

    @PatchMapping("/me")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {

    }

    @PatchMapping("/me/image")
    public ResponseEntity<?> updateUserImage(@RequestBody UpdateImageDTO updateImageDTO) {

    }


}
