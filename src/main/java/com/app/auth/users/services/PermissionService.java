package com.app.auth.users.services;

import com.app.auth.base.BaseService;
import com.app.auth.users.dto.PermissionDto;
import com.app.auth.users.entitites.Permission;
import com.app.auth.users.repositories.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PermissionService extends BaseService<Permission, PermissionDto> {
    @Autowired
    PermissionRepository repository;
    @Autowired
    ModelMapper modelMapper;

    public PermissionService(PermissionRepository repository, ModelMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.modelMapper = mapper;
    }
}
