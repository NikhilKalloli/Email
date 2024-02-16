package com.test.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        // send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nikhilkalloli0097@gmail.com");
        message.setTo(to);
        message.setSubject(subject);    
        message.setText(body);  
         
        mailSender.send(message);

        System.out.println("Mail Sent Successfully");
    }
}
