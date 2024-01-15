package com.cinema.authservice.controller;

import com.cinema.authservice.dto.ChangePasswordDto;
import com.cinema.authservice.exception.InvalidPasswordException;
import com.cinema.authservice.config.UserService;
import com.cinema.authservice.dto.AuthResponse;
import com.cinema.authservice.dto.GetUserResponse;
import com.cinema.authservice.dto.UserUpdateRequest;
import com.cinema.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cinema")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CinemaController {
    @Autowired
    private final AuthService authService;
    @Autowired
    private final UserService userService;
    @GetMapping("/")
    public ResponseEntity getUser() {
        GetUserResponse user = authService.getUser(userService.getUser().getId());
        if (user!=null){
            return new ResponseEntity(user,HttpStatus.OK);
        }else {
            return new ResponseEntity("User Not found",HttpStatus.NOT_FOUND);
        }

    }
    @PatchMapping("/")
    public ResponseEntity updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        try {
            if (userUpdateRequest.getContact() == null ||
                    userUpdateRequest.getAddress() == null ||
                    userUpdateRequest.getName() == null) {
                return new ResponseEntity("Please input required field  ", HttpStatus.BAD_REQUEST);
            }
            AuthResponse authResponse = authService.updateUser(userUpdateRequest);
            return new ResponseEntity(authResponse, HttpStatus.OK);
        } catch (InvalidPasswordException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Handle other exceptions if needed
            return new ResponseEntity("Something went wrong. Try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PatchMapping("/password")
    public ResponseEntity changePassword(@RequestBody ChangePasswordDto changePasswordDto) {

        try {
            if (changePasswordDto.getOldPassword() == null ||
                    changePasswordDto.getNewPassword() == null ||
                    changePasswordDto.getConfirmPassword() == null) {
                return new ResponseEntity("Please input required field  ", HttpStatus.BAD_REQUEST);
            } else if (!changePasswordDto.getNewPassword().matches(changePasswordDto.getConfirmPassword())) {
                return new ResponseEntity("New password and confirm password do not match", HttpStatus.BAD_REQUEST);
            } else if (!isValidPassword(changePasswordDto.getNewPassword())) {
                return new ResponseEntity("Invalid new password format", HttpStatus.BAD_REQUEST);
            }

            AuthResponse authResponse = authService.changePassword(changePasswordDto.getNewPassword(),changePasswordDto.getOldPassword());
            System.out.println(authResponse);
            return new ResponseEntity(authResponse, HttpStatus.OK);
        } catch (InvalidPasswordException e) {

            System.out.println(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
    private boolean isValidPassword(String password) {

        return password != null && password.matches("^(?=.*[0-9])(?=.*[A-Z]).{8,}$");
    }


}
