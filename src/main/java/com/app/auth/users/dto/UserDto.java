package com.app.auth.users.dto;

import com.app.auth.base.dtos.BaseDTO;
import com.app.auth.users.entitites.User;
import com.app.auth.validators.annotations.ValidEntityOid;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;







@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto extends BaseDTO {

    @ValidEntityOid(User.class)
    private UUID id;

    @NotNull
    private String firstName;
    
    @NotNull
    private String lastName;

    @NotNull
    private String email;
    
    @NotNull
    private String phone;

    @NotNull
    private String password;

    private Boolean isVerified;

    private List<UserRoleDto> roles;


    
}
