package com.toonborghuis.gallery.paintings_gallery.auth.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.toonborghuis.gallery.paintings_gallery.auth.utils.MailBody;

@Service
public class AuthEmailService {

    private final JavaMailSender javaMailSender;

    public AuthEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMessage(MailBody mailBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailBody.getTo());
        message.setFrom("luca.developertestmail@gmail.com");
        message.setSubject(mailBody.getSubject());
        message.setText(mailBody.getText());

        javaMailSender.send(message);
    }
}
