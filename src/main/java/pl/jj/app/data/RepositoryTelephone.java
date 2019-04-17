package pl.jj.app.data;

import org.springframework.data.repository.CrudRepository;
import pl.jj.app.entity.Telephone;

import java.util.List;

/**
 * @author JNartowicz
 */
public interface RepositoryTelephone extends CrudRepository<Telephone, Long> {
    List<Telephone> findTop10ByOrderByIdDesc();
}
