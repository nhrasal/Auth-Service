package com.app.auth.users.controllers;

import javax.validation.Valid;

import com.app.auth.users.dto.UserDTO;
import com.app.auth.users.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.auth.response.AppResponse;
import com.app.auth.users.entitites.User;
import com.app.auth.users.request.SignupRequest;
import com.app.auth.users.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/auth")
@Tag(name="User Controller")
public class UserController {
    @Autowired
    UserService service;

    @Autowired
    RoleService roleService;

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

    @PostMapping("signup")
    public UserDTO SignUp(@Valid @RequestBody SignupRequest requestData) {
         return service.signup(requestData);
        // try {
        //     return service.signup(requestData);
        // } catch (Exception ex) {
        //     return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        // }
    }
}
