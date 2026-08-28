package pl.edu.tpo_10.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HttpsUrlValidator implements ConstraintValidator <ValidHttpsUrl, String> {
   @Override
   public boolean isValid(String url, ConstraintValidatorContext constraintValidatorContext) {
      return url != null && url.startsWith("https://");
   }
}
