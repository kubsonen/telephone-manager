package pl.jj.app.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.jj.app.model.*;
import pl.jj.app.util.AppException;
import pl.jj.app.util.Const;
import pl.jj.util.generator.RandomString;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author JNartowicz
 */
@Service
@PropertySource("classpath:application.properties")
public class ServiceUser implements UserDetailsService {

    @Value("${address.url}")
    private String addressUrl;

    @Value("${default.user.username}")
    private String defaultUserName;

    @Value("${default.user.password}")
    private String defaultUserPassword;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private RepositoryAuthority repositoryAuthority;

    @Autowired
    private RepositoryInviteLink repositoryInviteLink;

    @Autowired
    private RepositoryResetLink repositoryResetLink;

    @Autowired
    private ServiceMail serviceMail;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user;

        if (s.equals(defaultUserName)) {

            //Sprawdzenie czy użytkownik domyślny znajduje się w bazie
            try {
                user = repositoryUser.findByUsername(defaultUserName).orElseThrow(() -> new UsernameNotFoundException(s));
                user.setAccountNotLock(true);
                user.setDefUser(true);
            } catch (UsernameNotFoundException e) {
                user = new User();
                user.setUsername(defaultUserName);
                user.setPassword(passwordEncoder.encode(defaultUserPassword));
                user.setAccountNotLock(true);
                user.setDefUser(true);
                user.addAuthority(new Authority("manager"));
                user = repositoryUser.save(user);
            }
        } else {

            //Firstly find by username
            try {
                user = repositoryUser.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(s));
            } catch (UsernameNotFoundException u) {
                //Secondly find by email
                user = repositoryUser.findByEmail(s).orElseThrow(() -> new UsernameNotFoundException(s));
            }
        }

        //Initialize authorities
        user.getAuthorities().size();

        return user;
    }

    @Transactional
    public User findByUsername(String username) {
        return repositoryUser.findByUsername(username).orElse(null);
    }

    @Transactional
    public void addAuthorityForUser(String username, String authority) throws Throwable {
        if (authority == null || authority.isEmpty()) throw new AppException("Cannot obtain authority value.");
        Authority auth = repositoryAuthority.findByAuthority(authority);
        if (auth == null) {
            auth = new Authority(authority);
        }
        User user = findByUsername(username);
        if (user == null) throw new AppException("User not found.");
        user.getAuthorities().size();
        user.addAuthority(auth);
    }

    @Transactional
    public List<User> getAllUsers() {
        List<User> users = repositoryUser.findAll();
        long i = 0;
        for (User user : users) {

            for (Authority a : user.getAuthoritiesSet()) {
                if (Const.AUTH_MANAGER.equals(a.getAuthority())) user.setManager(true);
                if (Const.AUTH_ADMIN.equals(a.getAuthority())) user.setAdmin(true);
            }

            user.setIndex(i);
            i++;
        }
        return users;
    }

    @Transactional
    public User saveUser(User user) {
        return repositoryUser.save(user);
    }

    @Transactional
    public User lockUser(Long id, boolean unlock) throws AppException {
        User user = repositoryUser.findById(id).get();

        for (Authority a : user.getAuthoritiesSet()) {
            if (Const.AUTH_ADMIN.equals(a.getAuthority()) || Const.AUTH_MANAGER.equals(a.getAuthority()))
                throw new AppException("User is an admin or manager.");
        }

        user.setAccountNotLock(unlock);
        return user;
    }

    @Transactional(rollbackOn = Throwable.class)
    public void sendEmailInvite(String email) throws Throwable {

        //Generate invite token
        String token = RandomString.getRandomString();

        //Create invite link object
        InviteLink inviteLink = new InviteLink();
        Date actualDate = new Date();
        inviteLink.setRegisterToken(token);
        inviteLink.setEmail(email);
        inviteLink.setCreateTime(actualDate);
        inviteLink.setExpirationDate(Const.addDaysToDate(actualDate, Const.EXPIRATION_DAYS));
        repositoryInviteLink.save(inviteLink);

        //Generate email
        Email emailMessage = new Email.EmailBuilder()
                .setTittle("Invitation link to Telephone Manager")
                .addRecipient(email)
                .addHeader("Hello !!!")
                .addParagraph("Let's join to this nice site and make your work more efficient.")
                .addParagraph("Click below on your invitation link and complete your registration.")
                .addLink("http://" + addressUrl + "/register?token=" + token, ">>> Click here <<<").build();

        serviceMail.sendMail(emailMessage);

    }

    @Transactional
    public InviteLink findByToken(String token) {
        return repositoryInviteLink.findByRegisterToken(token);
    }

    @Transactional
    public void registerUser(RegisterForm registerForm) throws Throwable {

        InviteLink inviteLink = repositoryInviteLink.findByRegisterToken(registerForm.getRegisterToken());
        if (inviteLink == null) throw new AppException("Invite link not found.");
        if (inviteLink.isUsed() == true)
            throw new AppException("Invite link was used.");
        if (!inviteLink.getEmail().equals(registerForm.getEmail())) throw new AppException("Token and email mismatch.");
        inviteLink.setUsed(true);

        //Create an user
        User user = new User();
        user.setEmail(inviteLink.getEmail());
        user.setUsername(registerForm.getLogin());
        user.setPassword(passwordEncoder.encode(registerForm.getPassword()));
        user.setInviteLink(inviteLink);
        user.setAccountNotLock(true);
        repositoryUser.save(user);

    }

    @Transactional
    public User getLoginUser() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            for (Authority auth : user.getAuthoritiesSet()) {
                if (auth.getAuthority().equals(Const.AUTH_MANAGER)) {
                    user.setManager(true);
                }
                if (auth.getAuthority().equals(Const.AUTH_ADMIN)) {
                    user.setAdmin(true);
                }
            }
            return user;
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    @Transactional
    public User findByEmail(String mail) {
        return repositoryUser.findByEmail(mail).get();
    }

    @Transactional
    public void resetPasswordRequest(ResetPassword resetPassword) throws Throwable {

        String newPassword = resetPassword.getResetPassword();
        String email = resetPassword.getResetEmail();
        if (newPassword == null || newPassword.isEmpty()) throw new AppException("Password is empty.");

        User user = findByEmail(resetPassword.getResetEmail());
        ResetLink resetLink = new ResetLink();
        resetLink.setNewEncryptedPassword(passwordEncoder.encode(resetPassword.getResetPassword()));

        //Generate token
        String token = RandomString.getRandomString();

        Date actualDate = new Date();
        resetLink.setRegisterToken(token);
        resetLink.setEmail(email);
        resetLink.setCreateTime(actualDate);
        resetLink.setExpirationDate(Const.addDaysToDate(actualDate, Const.EXPIRATION_DAYS));
        repositoryResetLink.save(resetLink);

        //Add reset link to user
        user.getResetLinks().add(resetLink);

        //Generate email to send token
        Email.EmailBuilder emailBuilder = new Email.EmailBuilder();
        emailBuilder.setTittle("Reset your password on telephone manager application.");
        emailBuilder.addRecipient(email);
        emailBuilder.addHeader("Hello !!!")
                .addParagraph("Click link below to reset your password.")
                .addLink("http://" + addressUrl + "/resetPassword?token=" + token, ">>> Click here <<<");

        Email emailToSend = emailBuilder.build();
        serviceMail.sendMail(emailToSend);

    }

    @Transactional
    public User applyResetPassword(String token) {
        ResetLink link = repositoryResetLink.findByRegisterTokenAndUsedIsFalse(token).get();
        link.setUsed(true);
        User user = link.getUser();
        user.setPassword(link.getNewEncryptedPassword());
        return user;
    }

}

















