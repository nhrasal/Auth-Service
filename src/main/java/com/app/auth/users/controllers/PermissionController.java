package com.app.auth.users.controllers;

import com.app.auth.base.BaseController;
import com.app.auth.users.dto.PermissionDto;
import com.app.auth.users.entitites.Permission;
import com.app.auth.users.services.PermissionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permissions")
@Tag(name = "permissions")
//@RequiredArgsConstructor
public class PermissionController extends BaseController<Permission, PermissionDto> {
//    public PermissionService service;

    public PermissionController(PermissionService service) {
        super(service);
//        this.service=service;
    }


}
