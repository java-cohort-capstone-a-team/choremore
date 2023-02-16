package com.capstone.choremore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    public JavaMailSender emailSender;

    private String from = "noreply@choremore.net";

    public Boolean prepareAndSend(String to, String subject, String body) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(body);

        try {

            this.emailSender.send(msg);

            return true;

        } catch (Exception e) {

            System.err.println("Error sending email: " + e.getMessage());

            return false;

        }

    }

}
