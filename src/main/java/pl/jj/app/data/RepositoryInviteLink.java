package pl.jj.app.data;

import org.springframework.data.repository.CrudRepository;
import pl.jj.app.model.InviteLink;

public interface RepositoryInviteLink extends CrudRepository<InviteLink, Long> {
    InviteLink findByRegisterToken(String token);
}
