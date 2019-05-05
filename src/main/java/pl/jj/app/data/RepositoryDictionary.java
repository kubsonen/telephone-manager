package pl.jj.app.data;

import org.springframework.data.repository.CrudRepository;
import pl.jj.app.model.Dictionary;

/**
 * @author JNartowicz
 */
public interface RepositoryDictionary extends CrudRepository<Dictionary, Long> {
    Dictionary findByName(String name);
}
