package com.app.auth.validators.implementations;

import com.app.auth.base.BaseEntity;
import com.app.auth.base.RepositoryFactoryComponent;
import com.app.auth.validators.annotations.ValidEntityOid;
import java.util.UUID;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;


@Component 
@RequestScope
@RequiredArgsConstructor
public class EntityOidValidityCheckerExtended implements ConstraintValidator<ValidEntityOid, UUID> {

    private final RepositoryFactoryComponent repositoryFactoryComponent;

    private Class<? extends BaseEntity> entityClass;

    @Override
    public void initialize(ValidEntityOid annotation) {
        entityClass = annotation.value();
    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext constraintValidatorContext) {
        return id == null || repositoryFactoryComponent.getRepository(entityClass).findByIdAndIsDeleted(id, false).isPresent();
    }

}
