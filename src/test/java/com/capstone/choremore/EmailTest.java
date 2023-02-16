package com.capstone.choremore;

import com.capstone.choremore.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;
import org.springframework.util.Assert;

@SpringBootTest
public class EmailTest {

    @Autowired
    private EmailService emailDao;

    @Test
    void testEmailService() {

        try {

            Boolean sent = emailDao.prepareAndSend("jmkrsak@gmail.com", "Testing", "I work, hello world!");

            Assert.isTrue(sent, "EmailService ===> The email was not sent. Please check SMTP settings in application.properties.");

        }catch(MailException e){

            e.printStackTrace();

            System.out.println("EmailService ===> Error found. Read above.");

        }

        System.out.println("EmailService ===> The email was sent successfully.");

    }

}
