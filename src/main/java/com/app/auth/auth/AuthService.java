package com.app.auth.auth;

import com.app.auth.auth.dto.ForgotPassword;
import com.app.auth.auth.dto.Login;
import com.app.auth.auth.dto.OTPVerification;
import com.app.auth.auth.dto.ResetPassword;
import com.app.auth.base.email.EmailService;
import com.app.auth.base.redis.RedisRepo;
import com.app.auth.configuration.JwtService;
import com.app.auth.response.AppResponse;
import com.app.auth.users.dto.UserDto;
import com.app.auth.users.dto.VerificationDto;
import com.app.auth.users.entitites.User;
import com.app.auth.users.entitites.Verification;
import com.app.auth.users.repositories.UserRepository;
import com.app.auth.users.repositories.UserRoleRepository;
import com.app.auth.users.repositories.VerificationRepository;
import com.app.auth.users.services.UserService;
import com.app.auth.users.services.VerificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepo;
    private final UserService userService;
    private final VerificationService verificationService;
    private final UserRoleRepository userRoleRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final RedisRepo redisRepo;

    private final EmailService emailService;
    private final VerificationRepository verificationRepository;

    public AppResponse login(Login login) {

        Optional<User> user = userRepo.findByEmail(login.getEmail());
        if (user.isEmpty())
            return AppResponse.build(HttpStatus.NOT_FOUND).message("No user found!");

        if (!user.get().getIsActive())
            return AppResponse.build(HttpStatus.BAD_REQUEST).message("User is not active!.");

        if (!user.get().getIsVerified())
            return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message("Please verify your account");

        if (!passwordEncoder.matches(login.getPassword(), user.get().getPassword()))
            return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message("Your credentials is not valid!");

        try {

            Map<String, String> body = new HashMap<>();
            body.put("email", user.get().getEmail());
            body.put("firstName", user.get().getFirstName());
            body.put("lasName", user.get().getLastName());
            body.put("phone", user.get().getPhone());
            body.put("userId", user.get().getId().toString());

            var accessToken = jwtService.generateToken(user.get().getEmail());
            redisRepo.setValue(user.get().getEmail(), accessToken);

            Map<String, String> header = new HashMap<>();
            header.put("accessToken", accessToken);

            return AppResponse.build(HttpStatus.OK).message("login success").header(header).body(body);

        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.BAD_REQUEST).body(ex).message("failed");

        }

    }

    public AppResponse forgotPassword(ForgotPassword forgotPassword) {
        return AppResponse.build(HttpStatus.NOT_FOUND).message("Currently this method is not work");
    }

    @Transactional
    public AppResponse accountVerification(String token) {

        Optional<Verification> findVerification = verificationRepository.findByTokenAndIsActive(token, true);

        if (findVerification.isEmpty())
            return AppResponse.build(HttpStatus.NOT_FOUND).message("Verification token not found");

        if (findVerification.get().getIsVerified())
            return AppResponse.build(HttpStatus.UNAUTHORIZED).message("This Verification link already verified!");

        if (findVerification.get().getExpireAt().before(new Date()))
            return AppResponse.build(HttpStatus.BAD_REQUEST).message("This link already expired!");

        if (findVerification.get().getUser().getIsVerified())
            return AppResponse.build(HttpStatus.BAD_REQUEST).message("Account already verified!");

        UserDto user = new UserDto();
        user.setId(findVerification.get().getUser().getId());
        user.setIsVerified(true);
        user.setIsActive(true);
        UserDto u = userService.update(user);

        if (u.getId() != null) {
            VerificationDto verification = new VerificationDto();
            verification.setId(findVerification.get().getId());
            verification.setIsVerified(true);
            verificationService.update(verification);
        }
        return AppResponse.build(HttpStatus.BAD_REQUEST).message("Account Verification successfully!");


    }

    public AppResponse otpVerification(OTPVerification otpVerification) {
        return AppResponse.build(HttpStatus.NOT_FOUND).message("Currently this method is not work");
    }

    public AppResponse resetPassword(ResetPassword resetPassword) {
        return AppResponse.build(HttpStatus.NOT_FOUND).message("Currently this method is not work");
    }
}
