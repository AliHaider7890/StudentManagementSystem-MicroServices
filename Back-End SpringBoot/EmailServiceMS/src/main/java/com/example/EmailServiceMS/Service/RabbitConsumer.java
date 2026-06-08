package com.example.EmailServiceMS.Service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "email-queue")
    public void consume(String email) {
        System.out.println("Received Email = " + email);
        emailService.sendEmailByRabbitMQ(email);
    }
}