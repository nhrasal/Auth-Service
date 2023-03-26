package com.app.auth.auth;

import com.app.auth.auth.dto.ForgotPassword;
import com.app.auth.auth.dto.Login;
import com.app.auth.auth.dto.OTPVerification;
import com.app.auth.auth.dto.ResetPassword;
import com.app.auth.response.AppResponse;
import com.app.auth.users.request.SignupRequest;
import com.app.auth.users.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Auth Controller")
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;
    private final UserService userService;

    @PostMapping("signup")
    public AppResponse SignUp(@Valid @RequestBody SignupRequest requestData) {
        try {
            return userService.signup(requestData);
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @PostMapping("login")
    public AppResponse login(@Valid @RequestBody Login login) {
        try {
            return service.login(login);
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @PostMapping("forgot-password")
    public AppResponse forgotPassword(@Valid @RequestBody ForgotPassword forgotPassword) {
        try {
            return service.forgotPassword(forgotPassword);
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @GetMapping("account-verification")
    public AppResponse accountVerification(@RequestParam String token){
        try {
            return service.accountVerification(token);
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @PostMapping("otp-verification")
    public AppResponse otpVerification(@Valid @RequestBody OTPVerification otpVerification) {
        try {
            return service.otpVerification(otpVerification);
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @PostMapping("reset-password")
    public AppResponse resetPassword(@Valid @RequestBody ResetPassword resetPassword) {
        try {
            return service.resetPassword(resetPassword);
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

}
