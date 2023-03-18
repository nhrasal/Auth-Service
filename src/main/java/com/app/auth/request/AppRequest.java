package com.app.auth.request;

import com.app.auth.base.model.MetaModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.Valid;
import lombok.Data;



/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 07, 2023
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppRequest<T> {

    private MetaModel meta;

    @Valid
    private T body;

}
