package com.app.auth.base.dtos;

import java.util.UUID;

public interface IdHolderRequestBodyDTO extends EmptyRequestBodyDTO {

    UUID getId();

    void setId(UUID id);

}
