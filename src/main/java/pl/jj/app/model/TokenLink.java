package pl.jj.app.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author JNartowicz
 */
@Getter
@Setter
@MappedSuperclass
public class TokenLink extends CommonEntity{

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "email")
    private String email;

    @Column(name = "register_token")
    private String registerToken;

    @Column(name = "used", columnDefinition = "BOOLEAN default false")
    private boolean used;

}
