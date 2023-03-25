package com.app.auth.users.controllers;

import com.app.auth.response.AppResponse;
import com.app.auth.users.request.RoleRequest;
import com.app.auth.users.services.RoleService;
import com.app.auth.users.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@RestController
@RequestMapping("/users")
@Tag(name = "User Controller")
@RequiredArgsConstructor

public class UserController {

    public final UserService service;
    public final RoleService roleService;


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
