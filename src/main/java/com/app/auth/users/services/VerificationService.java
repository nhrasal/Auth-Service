package com.app.auth.users.services;

import com.app.auth.base.BaseService;
import com.app.auth.base.email.EmailDto;
import com.app.auth.base.email.EmailService;
import com.app.auth.users.dto.VerificationDto;
import com.app.auth.users.entitites.User;
import com.app.auth.users.entitites.Verification;
import com.app.auth.users.repositories.VerificationRepository;
import com.app.auth.utils.UtilsService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class VerificationService extends BaseService<Verification, VerificationDto> {

    @Autowired
    VerificationRepository repository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    @Value("${spring.baseUrl}")
    private String baseUrl;

    public VerificationService(VerificationRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper);
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public void storeVerification(User dto, String msg, String type) {
        String token = passwordEncoder.encode(dto.getEmail());
        VerificationDto data = new VerificationDto();
        data.setExpireAt(UtilsService.dateTimeAddMinutes(5));
        data.setCode(UtilsService.getRandomNumberString());
        data.setMessage(msg);
        data.setUser(dto);
        data.setToken(token);
        data.setType(type);
        data.setIsActive(true);
        data.setIsDeleted(false);
        VerificationDto store = this.store(data);
        this.sendVerification(dto.getEmail(), token);

    }

//    Account verification mail throw
    private void sendVerification(String email, String token) {

        Map<String, Object> properties = new HashMap<>();
        properties.put("title", "Account Verification");
        properties.put("message", "");
        properties.put("url", baseUrl + "auth/account-verification?token=" + token);

        EmailDto dto = EmailDto.builder()
                .subject("Account Verification")
                .recipient(email)
                .messageBody("dear user ")
                .template("account-verification")
                .props(properties)
                .build();

        emailService.sendEmail(dto);

    }


}
