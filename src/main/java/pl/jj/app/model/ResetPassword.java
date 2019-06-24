package pl.jj.app.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author JNartowicz
 */
@Getter
@Setter
public class ResetPassword {
    private String resetEmail;
    private String resetPassword;
}
