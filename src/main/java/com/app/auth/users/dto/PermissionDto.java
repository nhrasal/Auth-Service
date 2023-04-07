package com.app.auth.users.dto;

import com.app.auth.base.dtos.BaseDTO;
import com.app.auth.users.entitites.Role;
import com.app.auth.validators.annotations.ValidEntityOid;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
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
public class PermissionDto extends BaseDTO {
    @ValidEntityOid(Role.class)
    private UUID id;

    @NotNull
    private String title;

    private String slug;

    private String description;

    private Boolean isActive;

    private Boolean isDeleted;
}
