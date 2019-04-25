package pl.jj.app.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.jj.app.entity.User;

import javax.transaction.Transactional;

/**
 * @author JNartowicz
 */
@Service
public class ServiceUser implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RepositoryUser repositoryUser;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        String defUsername = "pata";
        repositoryUser.findByUsername(defUsername).orElseGet(() -> {
            User user = new User();
            user.setUsername("pata");
            user.setPassword(passwordEncoder.encode("p123"));
            return repositoryUser.save(user);
        });

        User user = repositoryUser.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(s));
        user.getAuthorities().size();

        return user;
    }

    @Transactional
    public User findByUsername(String username){
        return repositoryUser.findByUsername(username).orElse(null);
    }

}
