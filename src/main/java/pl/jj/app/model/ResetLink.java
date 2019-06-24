package pl.jj.app.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author JNartowicz
 */
@Entity
@Getter
@Setter
@Table(name = "token_link")
public class ResetLink extends TokenLink {

    @Column(name = "new_encrypted_password")
    private String newEncryptedPassword;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
