package com.app.auth.auth;

import com.app.auth.auth.dto.ForgotPassword;
import com.app.auth.auth.dto.Login;
import com.app.auth.auth.dto.ResetPassword;
import com.app.auth.base.redis.RedisRepo;
import com.app.auth.configuration.JwtService;
import com.app.auth.constants.Constants;
import com.app.auth.response.AppResponse;
import com.app.auth.users.dto.UserDto;
import com.app.auth.users.dto.VerificationDto;
import com.app.auth.users.entitites.User;
import com.app.auth.users.entitites.Verification;
import com.app.auth.users.repositories.UserRepository;
import com.app.auth.users.repositories.VerificationRepository;
import com.app.auth.users.services.UserService;
import com.app.auth.users.services.VerificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepo;
    private final UserService userService;
    private final VerificationService verificationService;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final RedisRepo redisRepo;

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

    public AppResponse forgotPassword(ForgotPassword forgotPassword) {
        Optional<User> user = userRepo.findByEmail(forgotPassword.getEmail());

        if (user.isEmpty())
            return AppResponse.build(HttpStatus.NOT_FOUND).message("User is not valid!");

        if (!user.get().getIsVerified())
            return AppResponse.build(HttpStatus.NOT_FOUND).message("User Account is not verified!");

        Optional<Verification> verification = verificationRepository.findFirstByUserAndTypeOrderByCreatedOnDesc(user.get(), Constants.ACCOUNT_FORGOT);

        if (verification.isPresent() && !verification.get().getIsVerified() && verification.get().getIsActive()) {
            if (verification.get().getExpireAt().after(new Date()))
                return AppResponse.build(HttpStatus.BAD_REQUEST).message("Your verification link is not expired");
        }

        verificationService.storeVerification(user.get(), "forgot password", Constants.ACCOUNT_FORGOT);

        return AppResponse.build(HttpStatus.OK).message("Verification mail send, please check your mail!");

    }


    @Transactional
    public AppResponse forgotVerification(String token) {

        Optional<Verification> findVerification = verificationRepository.findByTokenAndIsActive(token, true);

        if (findVerification.isEmpty())
            return AppResponse.build(HttpStatus.NOT_FOUND).message("Verification token not found");

        if (findVerification.get().getIsVerified())
            return AppResponse.build(HttpStatus.UNAUTHORIZED).message("This Verification link already verified!");

        if (findVerification.get().getExpireAt().before(new Date()))
            return AppResponse.build(HttpStatus.BAD_REQUEST).message("This link already expired!");

        System.out.println(findVerification.get().getIsVerified());

        if (findVerification.get().getIsVerified())
            return AppResponse.build(HttpStatus.BAD_REQUEST).message("Link already verified!");

        VerificationDto verification = new VerificationDto();
        verification.setId(findVerification.get().getId());
        verification.setIsVerified(true);
        VerificationDto updateVerify = verificationService.update(verification);
        String passwordResetToken = passwordEncoder.encode(updateVerify.getUser().getId().toString());
        if (updateVerify.getIsVerified()) {
            UserDto user = new UserDto();
            user.setId(updateVerify.getUser().getId());
            user.setPasswordResetToken(passwordResetToken);
            UserDto userUpdate = userService.update(user);

            Map<String, String> header = new HashMap<>();
            header.put("passwordToken", passwordResetToken);
            header.put("userId", user.getId().toString());

            return AppResponse.build(HttpStatus.OK).message(Constants.UPDATE_SUCCESS).header(header);
        }

        return AppResponse.build(HttpStatus.BAD_REQUEST).message(Constants.UPDATE_FAILED);

    }


//    public AppResponse otpVerification(OTPVerification otpVerification) {
//        return AppResponse.build(HttpStatus.NOT_FOUND).message("Currently this method is not work");
//    }

    public AppResponse resetPassword(ResetPassword resetPassword, String token) {

        if (token == null)
            return AppResponse.build(HttpStatus.NOT_FOUND).message("Password token is required from header");

        Optional<User> findUser = userRepo.findFirstByPasswordResetToken(token);
        if (findUser.isEmpty())
            return AppResponse.build(HttpStatus.NOT_FOUND).message("Token is not valid");

        if (!findUser.get().getIsActive())
            return AppResponse.build(HttpStatus.BAD_REQUEST).message("User is not active!");

        if (!findUser.get().getIsVerified())
            return AppResponse.build(HttpStatus.BAD_REQUEST).message("User is not Verified!");

        if (!resetPassword.getPassword().equals(resetPassword.getConfirmPassword()))
            return AppResponse.build(HttpStatus.CONFLICT).message("Password and confirm password is not same");

        if (passwordEncoder.matches(resetPassword.getPassword(), findUser.get().getPassword()))
            return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message("Password and Old password almost same try to change");


        UserDto userUpdate = new UserDto();
        userUpdate.setId(findUser.get().getId());
        userUpdate.setPassword(passwordEncoder.encode(resetPassword.getPassword()));

        UserDto user = userService.update(userUpdate);

        return AppResponse.build(HttpStatus.CREATED).message("Password reset successfully, try to login");
    }

}
