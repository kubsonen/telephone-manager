package pl.jj.app.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.jj.app.entity.User;

import javax.transaction.Transactional;
import java.util.List;

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

        User user = repositoryUser.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(s));
        user.getAuthorities().size();

        return user;
    }

    @Transactional
    public User findByUsername(String username){
        return repositoryUser.findByUsername(username).orElse(null);
    }

    @Transactional
    public List<User> getAllUsers(){
        List<User> users = repositoryUser.findAll();
        long i = 0;
        for(User user: users) {
            user.setIndex(i);
            i++;
        }
        return users;
    }

}
