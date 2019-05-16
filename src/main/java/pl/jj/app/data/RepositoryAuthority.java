package pl.jj.app.data;

import org.springframework.data.repository.CrudRepository;
import pl.jj.app.model.Authority;

public interface RepositoryAuthority extends CrudRepository<Authority, Long> {
    Authority findByAuthority(String authority);
}
