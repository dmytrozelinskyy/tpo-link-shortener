package pl.edu.tpo_10.repository;

import org.springframework.data.repository.CrudRepository;
import pl.edu.tpo_10.model.Link;

import java.util.Optional;

public interface LinkRepository extends CrudRepository<Link, String>
{
   boolean existsByTargetURL(String targetURL);
   Optional<Link> findByIdAndPassword(String id, String password);
}
