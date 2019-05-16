package pl.jj.app.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class InviteMail {

    private String mail;

    public void setMail(String mail) {
        this.mail = (mail != null ? mail.trim() : "");
    }

}
