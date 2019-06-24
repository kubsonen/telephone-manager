package pl.jj.app.model;

import lombok.AllArgsConstructor;
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

    public Authority() {
    }

    public Authority(String authority) {
        this.authority = authority;
    }
}
