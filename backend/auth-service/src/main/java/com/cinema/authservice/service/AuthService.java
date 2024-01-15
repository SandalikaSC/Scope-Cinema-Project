package com.cinema.authservice.service;

import com.cinema.authservice.dto.*;

import java.text.ParseException;

public interface AuthService {
    AuthResponse register(SignUpRequest request) throws Exception;

    AuthResponse authenticate(AuthRequest request);

    Object forgetPassword(ForgetPwRequest request);

    Object resetPassword(ResetPwRequest request);

    boolean validateResetToken(String token) throws ParseException;

    GetUserResponse getUser(long id);

    AuthResponse updateUser(UserUpdateRequest userUpdateRequest);

    AuthResponse changePassword(String changePasswordDto, String oldPassword);
}
