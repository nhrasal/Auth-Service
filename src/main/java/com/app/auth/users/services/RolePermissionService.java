package com.app.auth.users.services;

import com.app.auth.base.BaseService;
import com.app.auth.users.dto.RolePermissionDto;
import com.app.auth.users.entitites.RolePermission;
import com.app.auth.users.repositories.RolePermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RolePermissionService extends BaseService<RolePermission, RolePermissionDto> {

    @Autowired
    RolePermissionRepository repository;
    ModelMapper modelMapper;

    public RolePermissionService(RolePermissionRepository repository, ModelMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.modelMapper = mapper;
    }
}
