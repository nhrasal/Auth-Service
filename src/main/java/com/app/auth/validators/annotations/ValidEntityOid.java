package com.app.auth.validators.annotations;


import com.app.auth.base.BaseEntity;
import com.app.auth.validators.implementations.EntityOidValidityCheckerExtended;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;





@Constraint(validatedBy = EntityOidValidityCheckerExtended.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEntityOid {

    Class<? extends BaseEntity> value();
    String message() default "invalid oid provided";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}