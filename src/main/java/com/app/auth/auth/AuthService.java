package com.app.auth.auth;

import com.app.auth.auth.dto.ForgotPassword;
import com.app.auth.auth.dto.Login;
import com.app.auth.auth.dto.OTPVerification;
import com.app.auth.auth.dto.ResetPassword;
import com.app.auth.response.AppResponse;
import com.app.auth.users.repositories.RoleRepository;
import com.app.auth.users.repositories.UserRepository;
import com.app.auth.users.repositories.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepo;
    private final UserRoleRepository userRoleRepo;
    private final RoleRepository roleRepo;

    public AppResponse login(Login login) {
        return AppResponse.build(HttpStatus.NOT_FOUND).message("Currently this method is not work");
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
