package com.example.EmailServiceMS.Controller;

import com.example.EmailServiceMS.Service.EmailService;
import com.example.EmailServiceMS.Service.RabbitProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class SendEmailController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private RabbitProducer rabbitProducer;
    @GetMapping("/test-api")
    public ResponseEntity<String> sentEmail(){

//        emailService.sendEmailByRabbitMQ();
        rabbitProducer.sendMessage();
        return ResponseEntity.ok("Email Sent");
    }

}
