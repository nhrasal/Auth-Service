package com.app.auth.users.dto;

import com.app.auth.base.dtos.BaseDTO;
import com.app.auth.users.entitites.User;
import com.app.auth.validators.annotations.ValidEntityOid;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerificationDto extends BaseDTO {

    @ValidEntityOid(User.class)
    private UUID id;

    private  User user;

    private String code;
    private  String token;
    private  String type;
    private  String message;
    private Date expireAt;
    private Boolean isVerified;

}
