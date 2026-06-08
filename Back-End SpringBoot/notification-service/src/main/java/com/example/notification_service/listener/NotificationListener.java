package com.example.notification_service.listener;

import com.example.notification_service.model.Course;
import com.example.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = "notification_queue")
    public void consumeCourseMessage(Course course) {
        log.info("📨 Message received: {}", course.getCourseName());
        log.info("🔥 LISTENER HIT!");
        System.out.println("🔥🔥🔥 MESSAGE RECEIVED: " );
        // Sirf SMS bhej
        notificationService.sendSmsNotification(course);

        log.info("✅ Done!");
    }
}