package pl.jj.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author JNartowicz
 */
@Getter
@Setter
@Entity
@Table(name = "t_user")
public class User extends CommonEntity implements UserDetails {

    @Column(name = "username", unique = true)
    private String username;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @Column(name = "first_name")
    private String firstName;

    @JsonIgnore
    @Column(name = "last_name")
    private String lastName;

    @JsonIgnore
    @Column(name = "email", unique = true)
    private String email;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "user_authority_link",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities = new HashSet<>();

    @Column(name = "account_not_lock", columnDefinition = "BOOLEAN default true")
    private boolean accountNotLock;

    @OneToOne
    @JoinColumn(name = "invite_link_id")
    @JsonIgnore
    private InviteLink inviteLink;

    @JsonIgnore
    @Column(name = "def_user", columnDefinition = "BOOLEAN default false")
    private boolean defUser;

    @OneToMany
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Set<ResetLink> resetLinks;

    @Transient
    private Long index;

    @Transient
    private boolean selected;

    @Transient
    private boolean manager;

    @Transient
    private boolean admin;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    public Set<Authority> getAuthoritiesSet() {
        return authorities;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNotLock;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isDefUser() {
        return defUser;
    }

    public void setDefUser(boolean defaultUser) {
        this.defUser = defaultUser;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void addAuthority(Authority authority){
        if(authorities == null){
            authorities = new HashSet<>();
        }
        authorities.add(authority);
    }



}
