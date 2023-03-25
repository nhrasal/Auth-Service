package com.app.auth.users.services;

import com.app.auth.base.BaseService;
import com.app.auth.users.dto.UserDto;
import com.app.auth.users.dto.VerificationDto;
import com.app.auth.users.entitites.User;
import com.app.auth.users.entitites.Verification;
import com.app.auth.users.repositories.UserRepository;
import com.app.auth.users.repositories.VerificationRepository;
import com.app.auth.utils.UtilsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class VerificationService extends BaseService<Verification, VerificationDto> {

    @Autowired
    VerificationRepository repository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    public VerificationService(VerificationRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper);
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public void storeVerification(User dto,String msg,String type){
        VerificationDto data=new VerificationDto();
        data.setExpireAt(UtilsService.dateTimeAddMinutes(5));
        data.setCode(UtilsService.getRandomNumberString());
        data.setMessage(msg);
        data.setUser(dto);
        data.setToken(passwordEncoder.encode(dto.getEmail()));
        data.setType(type);
        data.setIsActive(true);
        data.setIsDeleted(false);
//        data.setC
        VerificationDto store=this.store(data);

    }


}
