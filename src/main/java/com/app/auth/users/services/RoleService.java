package com.app.auth.users.services;

import com.app.auth.base.BaseService;
import com.app.auth.response.AppResponse;
import com.app.auth.users.dto.RoleDto;
import com.app.auth.users.entitites.Role;
import com.app.auth.users.repositories.RoleRepository;
import com.app.auth.users.request.RoleRequest;
import com.app.auth.util.UtilsService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
@Slf4j
@Service
public class RoleService extends BaseService<Role, RoleDto> {
    @Autowired
    RoleRepository repository;
    @Autowired
    ModelMapper modelMapper;
//    @Autowired
//    UtilsService utilsService;


    public RoleService(RoleRepository repository, ModelMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.modelMapper = mapper;
//        this.storeSuperAdmin();
    }

    public void storeSuperAdmin() {
        Optional<Role> findRole = repository.findBySlug("super-admin");
        System.out.println(findRole);
        if (findRole.isEmpty()) {
            RoleDto r = new RoleDto();
            r.setTitle("Super Admin");
            r.setSlug("super-admin");
            r.setDescription("This is super admin!");
            r.setIsActive(true);
            r.setIsDeleted(false);
            this.store(r);
        }

    }

    public AppResponse createRole(RoleRequest roleRequest) {
        String slug = UtilsService.toSlug(roleRequest.getTitle());
        Optional<Role> findRole = repository.findBySlug(slug);
        if (findRole.isPresent())
            return AppResponse.build(HttpStatus.CONFLICT).message("Role already exist");

        Role role = new Role();
        role.setTitle(roleRequest.getTitle());
        role.setDescription(roleRequest.getDescription());
        role.setSlug(slug);
        Role storeRole = repository.save(role);

        return AppResponse.build(HttpStatus.OK).body(storeRole).message("Role Create successfully");


    }


    public AppResponse updateRole(UUID id, RoleRequest roleRequest) {
        String slug = UtilsService.toSlug(roleRequest.getTitle());
        RoleDto roleDto = new RoleDto();
        roleDto.setId(id);
        roleDto.setTitle(roleRequest.getTitle());
        roleDto.setDescription(roleRequest.getDescription());
        roleDto.setSlug(slug);
        RoleDto updateRole = this.update(roleDto);
        return AppResponse.build(HttpStatus.OK).body(updateRole).message("Role Update successfully");
    }

    public AppResponse deleteRole(UUID id) {
        RoleDto deleteRole = this.delete(id);
        return AppResponse.build(HttpStatus.OK).message("Role Update successfully");
    }



    public Optional<Role> findBySlug(String slug) {
        return repository.findBySlug(slug);
    }
}
