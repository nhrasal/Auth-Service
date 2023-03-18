package com.app.auth.users.dto;

import com.app.auth.base.dtos.BaseDTO;
import com.app.auth.users.entitites.User;
import com.app.auth.validators.annotations.ValidEntityOid;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;







@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends BaseDTO {

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
    
}
