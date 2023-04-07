package com.app.auth.users.dto;

import com.app.auth.base.dtos.BaseDTO;
import com.app.auth.users.entitites.UserRole;
import com.app.auth.validators.annotations.ValidEntityOid;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPermissionDto extends BaseDTO {
    @ValidEntityOid(UserRole.class)
    private UUID id;

    private UserDto user;
    private PermissionDto permission;

    private Boolean hasPermitted;
}
