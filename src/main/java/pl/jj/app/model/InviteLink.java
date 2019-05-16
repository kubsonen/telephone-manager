package pl.jj.app.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "invite_link")
public class InviteLink extends CommonEntity {

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "email")
    private String email;

    @Column(name = "register_token")
    private String registerToken;

    @Column(name = "used")
    private Boolean used;

}


