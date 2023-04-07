package com.app.auth.users.services;

import com.app.auth.base.BaseService;
import com.app.auth.users.dto.UserPermissionDto;
import com.app.auth.users.entitites.UserPermission;
import com.app.auth.users.repositories.UserPermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserPermissionService extends BaseService<UserPermission, UserPermissionDto> {
    @Autowired
    UserPermissionRepository repository;
    ModelMapper modelMapper;

    public UserPermissionService(UserPermissionRepository repository, ModelMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.modelMapper = mapper;
    }
}
