package pl.jj.app.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author JNartowicz
 */
@Getter
@Setter
@Entity
@Table(name = "authority")
public class Authority extends CommonEntity implements GrantedAuthority {

    @Column(name = "authority", unique = true)
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

}
