package pl.jj.app.data;

import org.springframework.data.repository.CrudRepository;
import pl.jj.app.entity.Dictionary;

/**
 * @author JNartowicz
 */
public interface RepositoryDictionary extends CrudRepository<Dictionary, Long> {
}
