package com.mindspark.family_finances.controllers;

import com.mindspark.family_finances.services.mailsender.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home-page")
@RequiredArgsConstructor
public class homePageController {

    private final MailSenderService mailSenderService;

    @GetMapping
    public String homePage(){
        return "HomePage";
    }

    @GetMapping("/mail")
    public String sendMail() {
        mailSenderService.requestToJoin(
                "zsydun@gmail.com",
                "Hello",
                "First Hello!"
        );
        return "Mail sent successfully!";
    }
}
