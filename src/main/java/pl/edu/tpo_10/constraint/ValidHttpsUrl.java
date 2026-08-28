package pl.edu.tpo_10.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = HttpsUrlValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidHttpsUrl {
   String message() default "{url.invalid}";
   Class<?>[] groups() default {};
   Class<? extends Payload>[] payload() default {};
}
