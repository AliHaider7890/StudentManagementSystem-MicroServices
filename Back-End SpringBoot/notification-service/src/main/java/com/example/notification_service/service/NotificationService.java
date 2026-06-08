package com.example.notification_service.service;

import com.example.notification_service.model.Course;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
@Slf4j
public class NotificationService {

    private final SmsService smsService;

    public NotificationService(SmsService smsService) {
        this.smsService = smsService;
    }

    // ✅ YEH EK HI METHOD RAHKHO - Jo Twilio se real SMS bhejta hai
    public void sendSmsNotification(Course course) {
        // 🔥 APNA NUMBER YAHAN DAAL
        String myPhoneNumber = "+966596959826";  // ← APNA NUMBER DAAL

        String message = String.format(
                "New Course Created!\n\nCourse: %s\nDescription: %s",
                course.getCourseName(),
                course.getDescription()
        );

        smsService.sendSms(myPhoneNumber, message);
        log.info("📱 Real SMS sent for course: {}", course.getCourseName());
    }

    // ✅ Email ke liye (optional - rakh sakte ho)
    public void sendEmailNotification(Course course) {
        log.info("📧 EMAIL would be sent for: {}", course.getCourseName());
    }

    // ✅ Push ke liye (optional - rakh sakte ho)
    public void sendPushNotification(Course course) {
        log.info("🔔 PUSH notification for: {}", course.getCourseName());
    }
}