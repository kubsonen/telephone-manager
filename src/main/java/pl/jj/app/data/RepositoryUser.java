package pl.jj.app.data;

import org.springframework.data.repository.CrudRepository;
import pl.jj.app.entity.User;

/**
 * @author JNartowicz
 */
public interface RepositoryUser extends CrudRepository<User,  Long> {
}
