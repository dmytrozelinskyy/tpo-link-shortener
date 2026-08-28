package pl.edu.tpo_10.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = UniqueUrlValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUrl {
   String message() default "{url.duplicate}";
   Class<?>[] groups() default {};
   Class<? extends Payload>[] payload() default {};
}
