package com.cinema.authservice.service.serviceImpl;
import com.cinema.authservice.config.UserService;
import com.cinema.authservice.kafka.kafkaDTO.KafkaCinemaDTO;
import com.cinema.authservice.exception.InvalidPasswordException;
import com.cinema.authservice.config.JwtService;
import com.cinema.authservice.dto.*;
import com.cinema.authservice.entity.*;
import com.cinema.authservice.repository.ForgetPwTokenRepository;
import com.cinema.authservice.repository.TokenRepository;
import com.cinema.authservice.repository.UserRepository;
import com.cinema.authservice.service.AuthService;
import com.cinema.authservice.util.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Autowired
    private EmailSender emailSender;
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final ForgetPwTokenRepository forgetPwRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final UserService userService;
    @Autowired
    private KafkaTemplate<String, KafkaCinemaDTO> kafkaTemplate;
    @Value("${frontend.url}")
    private String frontendUrl;

    //cinema registration
    @Override
    public AuthResponse register(SignUpRequest request) throws Exception {
        
        Optional<User> existUser = repository.findByEmail(request.getEmail());
        if (existUser.isPresent()){
            throw new Exception("User Already exist");
            
        }
        User user = User.builder()
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .address(request.getAddress())
                .contact(request.getContact())
                .role(Role.CINEMA)
                .build();
        var savedUser = repository.save(user);
        kafkaTemplate.send("New_Cinema_Topic", KafkaCinemaDTO.builder()
                        .cinemaId(savedUser.getId())
                        .cinemaName(savedUser.getName())
                        .address(savedUser.getAddress())
                        .contact(savedUser.getContact())
                .build());



        var jwtToken = jwtService.generateToken(user);
        saveUserToken(jwtToken, savedUser);
        return AuthResponse.builder()
                .userName(savedUser.getName())
                .accessToken(jwtToken)
                .build();
    }
//save access token
    private void saveUserToken(String jwtToken, User savedUser) {
        var token= Token.builder()
                .tokenType(TokenType.BEARER)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .user(savedUser)
                .build();
        tokenRepository.save(token);
    }

    //cinema login
    @Override
    public AuthResponse authenticate(AuthRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(jwtToken,user);


        return AuthResponse.builder()
                .accessToken(jwtToken)
                .userName(user.getName())
                .build();
    }
    //forget password-validate email
    @Override
    public Object forgetPassword(ForgetPwRequest request) {
        User user = repository.findByEmail(request.getEmail()).orElseThrow(() -> new NoSuchElementException("User not found"));

        ForgetPasswordToken token = new ForgetPasswordToken();
        token.setToken(generateRandomToken());
        token.setExpiryTime(new Date(System.currentTimeMillis() + 3600000));
        token.setUser(user);
        token.setExpired(false);

        saveForgetPasswordToken(token);
        sendForgetPasswordEmail(user, token);

        return true;
    }
//reset password
    @Override
    public Object resetPassword(ResetPwRequest request) {
        String token = request.getToken();
        String newPassword = request.getPassword();

        ForgetPasswordToken forgetPasswordToken = forgetPwRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired reset token"));

        if (forgetPasswordToken.getExpiryTime().before(new Date())) {
            throw new RuntimeException("Reset token has expired");
        }

        User user = forgetPasswordToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        repository.save(user);


        forgetPasswordToken.setExpired(true);
        forgetPwRepository.save(forgetPasswordToken);
        return true;
    }
    //forget password-validate forget password token
    @Override
    public boolean validateResetToken(String token) throws ParseException {
        ForgetPasswordToken forgetPasswordToken=forgetPwRepository.findTopByTokenOrderByExpiryTimeDesc(token).orElseThrow(() -> new NoSuchElementException("token not found"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        Date expiryTime = dateFormat.parse(String.valueOf(forgetPasswordToken.getExpiryTime()));

        return !isTokenExpired(expiryTime);

    }

    @Override
    public GetUserResponse getUser(long id) {
        User user=repository.findById(id).orElseThrow();

        return GetUserResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .address(user.getAddress())
                .email(user.getEmail())
                .contact(user.getContact())
                .build();
    }

    @Override
    public AuthResponse updateUser(UserUpdateRequest userUpdateRequest) {

         User user=repository.findById(userService.getUser().getId()).orElseThrow();

        user.setAddress(userUpdateRequest.getAddress());
        user.setContact(userUpdateRequest.getContact());
        user.setName(userUpdateRequest.getName());

        //generate access token
        User savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(savedUser);
        revokeAllUserTokens(user);
        saveUserToken(jwtToken,user);
        kafkaTemplate.send("New_Cinema_Topic", KafkaCinemaDTO.builder()
                .cinemaId(savedUser.getId())
                .cinemaName(savedUser.getName())
                .address(savedUser.getAddress())
                .contact(savedUser.getContact())
                .build());


        return AuthResponse.builder()
                .accessToken(jwtToken)
                .userName(user.getName())
                .build();

    }

    //forget password-check forget password token expired
    private static boolean isTokenExpired(Date expiryTime) {
        Date currentTime = new Date();
        return currentTime.after(expiryTime);
    }
    private String generateRandomToken() {
        return UUID.randomUUID() + UUID.randomUUID().toString();
    }

    private void saveForgetPasswordToken(ForgetPasswordToken token) {
        forgetPwRepository.save(token);
    }

    private void sendForgetPasswordEmail(User user, ForgetPasswordToken token) {
        String subject = "Forget password verification";
        String message =  buildHtmlMessage(user.getName(),token.getToken());

        emailSender.sendSimpleEmail(user.getEmail(), subject, message);

    }
    //forget password email message
    private String buildHtmlMessage(String userName, String token) {
        return "Hello "+userName+", To reset your password, click the link, " + frontendUrl+"/reset-password/"+ token;
    }

   //revoke all the access tokens user currently have
    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
    @Override
    public AuthResponse changePassword(String newPassword, String oldPassword) {
        User user=repository.findById(userService.getUser().getId()).orElseThrow();
        if(!passwordEncoder.matches(oldPassword, user.getPassword())){
            throw new InvalidPasswordException("Given Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));

        var jwtToken = jwtService.generateToken(repository.save(user));
        revokeAllUserTokens(user);
        saveUserToken(jwtToken,user);


        return AuthResponse.builder()
                .accessToken(jwtToken)
                .userName(user.getName())
                .build();

    }

}
