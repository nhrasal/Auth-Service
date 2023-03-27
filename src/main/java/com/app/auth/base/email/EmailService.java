package com.app.auth.base.email;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Service
public class EmailService extends Thread {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${spring.mail.sender}")
    private String sender;


    public void send(EmailDto dto) {

        // Try block to check for exceptions
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(dto.getRecipient());
            mailMessage.setText(dto.getMessageBody());
            mailMessage.setSubject(dto.getSubject());
            javaMailSender.send(mailMessage);
            System.out.println("Mail Sent Successfully...");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    public void sendEmail(EmailDto mail) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariables(mail.getProps());

            String html = templateEngine.process(mail.getTemplate(), context);
            helper.setTo(mail.getRecipient());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(sender);
            javaMailSender.send(message);
            System.out.println("email send successfully");
        } catch (Exception ex) {
            System.out.println("Email send failed ::" + ex.getMessage());
        }
    }

}