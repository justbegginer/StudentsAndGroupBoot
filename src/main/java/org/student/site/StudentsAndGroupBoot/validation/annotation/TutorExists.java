package org.student.site.StudentsAndGroupBoot.validation.annotation;

import org.student.site.StudentsAndGroupBoot.validation.classes.TutorExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= TutorExistsValidator.class)
public @interface TutorExists {
    String message() default "Tutor with this id doesn't exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
