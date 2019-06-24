package pl.jj.app.data;

import org.springframework.data.repository.CrudRepository;
import pl.jj.app.model.ResetLink;

import java.util.Optional;

public interface RepositoryResetLink extends CrudRepository<ResetLink, Long> {
    Optional<ResetLink> findByRegisterTokenAndUsedIsFalse(String token);
}
