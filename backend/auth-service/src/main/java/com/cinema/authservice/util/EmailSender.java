package com.cinema.authservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {
    @Autowired
    private JavaMailSender mailSender;
//    @Value("${spring.mail.username}")
//    private String fromEmail;
    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String body
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("schamexo2017@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
            mailSender.send(message);


    }

}

