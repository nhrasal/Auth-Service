package com.app.auth.users.services;

import com.app.auth.base.BaseService;
import com.app.auth.users.dto.RoleDto;
import com.app.auth.users.entitites.Role;
import com.app.auth.users.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@SuppressWarnings("unused")
@Slf4j
@Service
public class RoleService extends BaseService<Role, RoleDto> {
    @Autowired
    RoleRepository repository;
    @Autowired
    ModelMapper modelMapper;

    public  RoleService(RoleRepository repository, ModelMapper mapper){
        super(repository,mapper);
        this.repository=repository;
        this.modelMapper=mapper;
        this.storeSuperAdmin();
    }

    public void storeSuperAdmin(){
        Optional<Role> findRole = repository.findBySlug("super-admin");
        System.out.println(findRole);
        if ( findRole.isEmpty()) {
            RoleDto r = new RoleDto();
            r.setTitle("Super Admin");
            r.setSlug("super-admin");
            r.setDescription("This is super admin!");
            r.setIsActive(true);
            r.setIsDeleted(false);
            this.store(r);
        }

    }

    public Optional<Role> findBySlug(String slug){
        return repository.findBySlug(slug);
    }
}
