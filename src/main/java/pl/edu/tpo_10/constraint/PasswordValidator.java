package pl.edu.tpo_10.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String>
{
   private boolean required;

   @Override
   public void initialize(ValidPassword constraintAnnotation) {
      this.required = constraintAnnotation.required();
   }

   @Override
   public boolean isValid(String pass, ConstraintValidatorContext constraintValidatorContext) {
      if (!required && (pass == null || pass.isEmpty())) return true;

      boolean containsLowercaseLetter = pass.matches(".*[a-z].*");
      boolean containsUppercaseLetter = pass.replaceAll("[^A-Z]", "").length() >= 2;
      boolean containsDigits = pass.replaceAll("[^0-9]", "").length() >= 3;
      boolean containsSpecial = pass.replaceAll("[a-zA-Z0-9]", "").length() >= 4;
      boolean isOfLength = pass.length() >= 10;

      constraintValidatorContext.disableDefaultConstraintViolation();

      if (!containsLowercaseLetter) constraintValidatorContext.buildConstraintViolationWithTemplate("{password.missingLowercaseLetter}").addConstraintViolation();
      if (!containsUppercaseLetter) constraintValidatorContext.buildConstraintViolationWithTemplate("{password.missingUppercaseLetter}").addConstraintViolation();
      if (!containsDigits) constraintValidatorContext.buildConstraintViolationWithTemplate("{password.missingDigits}").addConstraintViolation();
      if (!containsSpecial) constraintValidatorContext.buildConstraintViolationWithTemplate("{password.missingSpecialCharacters}").addConstraintViolation();
      if (!isOfLength) constraintValidatorContext.buildConstraintViolationWithTemplate("{password.tooShort}").addConstraintViolation();

      return containsLowercaseLetter && containsUppercaseLetter && containsDigits && containsSpecial && isOfLength;
   }
}
