package pl.jj.app.model;

import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
public class RegisterForm {

    @NotBlank
    private String registerToken;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    public String getRegisterToken() {
        return registerToken != null ? registerToken.trim() : null;
    }

    public String getEmail() {
        return email != null ? email.trim() : null;
    }

    public String getLogin() {
        return login != null ? login.trim() : null;
    }

    public String getPassword() {
        return password != null ? password.trim() : null;
    }
}
