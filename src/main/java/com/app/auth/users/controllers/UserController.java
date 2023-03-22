package com.app.auth.users.controllers;

import javax.validation.Valid;

import com.app.auth.users.dto.RoleDto;
import com.app.auth.users.dto.UserDTO;
import com.app.auth.users.request.RoleRequest;
import com.app.auth.users.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.app.auth.response.AppResponse;
import com.app.auth.users.entitites.User;
import com.app.auth.users.request.SignupRequest;
import com.app.auth.users.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;


@RestController
@RequestMapping("/user")
@Tag(name = "User Controller")
@RequiredArgsConstructor

public class UserController {

    public final UserService service;
    public final RoleService roleService;
//    @Autowired
//    UserService service;
//
//    @Autowired
//    RoleService roleService;

    @GetMapping
    public AppResponse getAllUser() {
        try {
            return service.getAll();
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @GetMapping("/roles")
    public AppResponse getAllRole() {
        try {
            return roleService.getAll();
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @PostMapping("/roles")
    public AppResponse createRole(@Valid @RequestBody RoleRequest roleRequest) {
        try {
            return roleService.createRole(roleRequest);
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }
    @PutMapping("/roles/{id}")
    public AppResponse updateRole(@PathVariable("id") UUID id, @Valid @RequestBody RoleRequest roleRequest) {
        try {
            return roleService.updateRole(id,roleRequest);
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @DeleteMapping("/roles/{id}")
    public AppResponse delete(@PathVariable("id") UUID id) {
        try {
            return roleService.deleteRole(id);
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

}
