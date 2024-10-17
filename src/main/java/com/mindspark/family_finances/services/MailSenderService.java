package com.mindspark.family_finances.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;


    public void sendMessage(String to, String subject, String body){

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        mailMessage.setFrom(from);

        mailSender.send(mailMessage);
        log.info("Mail with subj: '{}' was send to email: {}", subject, to);
    }

}
