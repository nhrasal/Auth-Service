package com.app.auth.base.dtos;


import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseDTO implements IdHolderRequestBodyDTO {

    @JsonIgnore
    private UUID createdBy;

    @JsonIgnore
    private Date createdOn;

    @JsonIgnore
    private UUID updatedBy;

    @JsonIgnore
    private Date updatedOn;

    @JsonIgnore
    private Boolean isDeleted;
    
    @JsonIgnore
    private Boolean isActive;
}