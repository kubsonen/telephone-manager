package pl.jj.app.data;

import org.springframework.data.repository.CrudRepository;
import pl.jj.app.entity.User;

import java.util.Optional;

/**
 * @author JNartowicz
 */
public interface RepositoryUser extends CrudRepository<User,  Long> {
    Optional<User> findByUsername(String username);
}
