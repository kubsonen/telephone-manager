package pl.jj.app.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import pl.jj.app.model.Email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Service
@PropertySource("classpath:mail.properties")
public class ServiceMail {

    @Value("${mail.smtp.auth}")
    private String smtpAuth;

    @Value("${mail.smtp.starttls.enable}")
    private String smtpStarttlsEnable;

    @Value("${mail.smtp.host}")
    private String smtpHost;

    @Value("${mail.smtp.port}")
    private String smtpPort;

    @Value("${mail.smtp.ssl.trust}")
    private String smtpSslTrust;

    @Value("${mail.email}")
    private String emailLogin;

    @Value("${mail.password}")
    private String mailPassword;

    public void sendMail(Email email) throws Throwable{

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", smtpAuth);
        prop.put("mail.smtp.starttls.enable", smtpStarttlsEnable);
        prop.put("mail.smtp.host", smtpHost);
        prop.put("mail.smtp.port", smtpPort);
        prop.put("mail.smtp.ssl.trust", smtpSslTrust);

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailLogin, mailPassword);
            }
        });

        Message message = new MimeMessage(session);

        for(Email.EmailAddress emailAddress: email.getSendTo()) message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddress.getEmailAddress()));
        for(Email.EmailAddress emailAddress: email.getCopyTo()) message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(emailAddress.getEmailAddress()));

        message.setSubject(email.getTittle());
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(email.getEmailContent(), "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        Transport.send(message);

    }

}
