package kr.tripamigo.tripamigo.util;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;

public class MailSendUtil {

    private MailSender mailSender;

    public void sendMailFromAdmin(String recipient, String sender, String message) throws Exception {
        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(recipient));
            mimeMessage.setFrom(new InternetAddress(sender));
            mimeMessage.setText(message);
        };

        try {
            this.mailSender.send((SimpleMailMessage) preparator);
        } catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }

}
