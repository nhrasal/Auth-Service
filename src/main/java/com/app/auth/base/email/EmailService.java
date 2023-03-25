package com.app.auth.base.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.sender}") private String sender;
    public void send(EmailDto dto)
    {

        // Try block to check for exceptions
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(dto.getRecipient());
            mailMessage.setText(dto.getMessageBody());
            mailMessage.setSubject(dto.getSubject());
//            mailMessage.setCc(dto.css);

            javaMailSender.send(mailMessage);
            System.out.println("Mail Sent Successfully...");
//            return "Mail Sent Successfully...";
        }
        // Catch block to handle the exceptions
        catch (Exception e) {
            System.out.println(e.toString());

//            return "Error while Sending Mail";
        }
    }
}