package com.cinema.authservice.controller;

import com.cinema.authservice.dto.*;
import com.cinema.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class authController {

    @Autowired
    private final AuthService service;

    //handle signup functionality
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody SignUpRequest request
    ) {
        if (!isValidContact(request.getContact()) || !isValidPassword(request.getPassword())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return ResponseEntity.ok(service.register(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
//check contact is valid
    private boolean isValidContact(String contact) {
        return contact != null && contact.matches("^0\\d{9}$");
    }
    private boolean isValidPassword(String password) {
        return password != null && password.matches("^(?=.*[0-9])(?=.*[A-Z]).{8,}$");
    }
    //Handle login functionality
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @Valid @RequestBody AuthRequest request
    ) {
        if(request.getPassword()==null || request.getEmail()==null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.authenticate(request));
    }
    //handle forget password-check given email
    @PostMapping("/forget-password")
    public ResponseEntity<Object> forgetPassword(
            @Valid      @RequestBody ForgetPwRequest request
    ) {
        if(request.getEmail()==null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.forgetPassword(request));
    }
    //handle forget password-reset password
    @PostMapping("/reset")
    public ResponseEntity<Object> resetPassword(
            @Valid    @RequestBody ResetPwRequest request
    ) {
        if(request.getPassword()==null||request.getConfirmPassword()==null|| !request.getPassword().matches(request.getConfirmPassword())||!isValidPassword(request.getPassword())){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.resetPassword(request));
    }
    //handle forget password-validate token
    @GetMapping("/reset-validate/{token}")
    public ResponseEntity<Boolean> validateResetToken(@PathVariable String token) throws ParseException {
        return ResponseEntity.ok(service.validateResetToken(token));

    }


}
