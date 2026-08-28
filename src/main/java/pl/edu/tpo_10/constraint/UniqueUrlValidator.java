package pl.edu.tpo_10.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import pl.edu.tpo_10.repository.LinkRepository;

@Component
public class UniqueUrlValidator implements ConstraintValidator<UniqueUrl, String> {
   private LinkRepository linkRepository;

   public UniqueUrlValidator(LinkRepository linkRepository) {
      this.linkRepository = linkRepository;
   }

   @Override
   public boolean isValid(String url, ConstraintValidatorContext context) {
      return url != null && !linkRepository.existsByTargetURL(url);
   }
}
