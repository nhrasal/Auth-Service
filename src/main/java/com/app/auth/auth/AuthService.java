package com.app.auth.auth;

import com.app.auth.auth.dto.ForgotPassword;
import com.app.auth.auth.dto.Login;
import com.app.auth.auth.dto.OTPVerification;
import com.app.auth.auth.dto.ResetPassword;
import com.app.auth.base.redis.RedisRepo;
import com.app.auth.configuration.JwtService;
import com.app.auth.response.AppResponse;
import com.app.auth.users.entitites.User;
import com.app.auth.users.repositories.RoleRepository;
import com.app.auth.users.repositories.UserRepository;
import com.app.auth.users.repositories.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepo;
    private final UserRoleRepository userRoleRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final RedisRepo redisRepo;

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
            body.put("firstName",user.get().getFirstName());
            body.put("lasName",user.get().getLastName());
            body.put("phone",user.get().getPhone());
            body.put("userId",user.get().getId().toString());




            var accessToken = jwtService.generateToken(user.get().getEmail());
            redisRepo.setValue(user.get().getEmail(), accessToken, 60);

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

    public AppResponse otpVerification(OTPVerification otpVerification) {
        return AppResponse.build(HttpStatus.NOT_FOUND).message("Currently this method is not work");
    }

    public AppResponse resetPassword(ResetPassword resetPassword) {
        return AppResponse.build(HttpStatus.NOT_FOUND).message("Currently this method is not work");
    }
}
