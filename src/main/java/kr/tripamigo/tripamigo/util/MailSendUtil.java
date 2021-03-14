package kr.tripamigo.tripamigo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSendUtil {

    @Autowired
    private JavaMailSender mailSender;
    public final static String DEFAULT_FROM_ADDRESS = "tripamigo@tripamigo.com";

    public void sendMail(String recipient, String sender, String subject, String message) throws Exception {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(recipient);
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        mailSender.send(simpleMailMessage);
    }

}
