package com.app.auth.validators.annotations;

import com.app.auth.validators.implementations.NotSelfReferencingParentValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;


@Constraint(validatedBy = NotSelfReferencingParentValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotSelfReferencingParent {

    String[] value();

    String message() default "Self-referencing parent";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}