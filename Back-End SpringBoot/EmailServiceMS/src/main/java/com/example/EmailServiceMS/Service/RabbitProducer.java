package com.example.EmailServiceMS.Service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage() {

        rabbitTemplate.convertAndSend(
                "email-queue",
                "alihaider58162@gmail.com"
        );
    }
}