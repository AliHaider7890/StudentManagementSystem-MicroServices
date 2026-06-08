//package com.example.EmailServiceMS.Service;
//
//import com.example.EmailServiceMS.Entity.Teacher;
//import com.example.EmailServiceMS.event.TeacherCreateEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.mail.javamail.MimeMessagePreparator;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    public void sendEmail(Teacher teacher){
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(teacher.getEmail());
//        message.setSubject("Welcome Teacher");
//        message.setText("Hello " + teacher.getName() + ", your account is created!");
//
//        mailSender.send(message);
//    }
// //   private final JavaMailSender javaMailSender;
//
//    @KafkaListener(topics = "teacher-created-topic")
//    public void listen(TeacherCreateEvent teacherCreateEvent) {
//        log.info("Got Message from teacher-create-Topic {}", teacherCreateEvent);
//
//        MimeMessagePreparator messagePreparator = mimeMessage -> {
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
//            messageHelper.setFrom("springshop@email.com");
//            messageHelper.setTo(teacherCreateEvent.getEmail());
//         //   messageHelper.setSubject(String.format("Your Order with OrderNumber %s is placed successfully", orderPlacedEvent.getOrderNumber()));
//            messageHelper.setText(String.format("""
//                Hi Customer,
//
//                Your order with order number %s is now placed successfully.
//
//                Best Regards
//                Aki Haider Shop
//                """, teacherCreateEvent.getTeacherName()));
//        };
//
//        try {
//            mailSender.send(messagePreparator);
//            log.info("Order Notification email sent!!");
//        } catch (Exception e) {
//            log.error("Exception occurred when sending mail", e);
//            throw new RuntimeException("Exception occurred when sending mail", e);
//        }
//    }
//}
package com.example.EmailServiceMS.Service;
import com.example.EmailServiceMS.Entity.Teacher;
import com.example.EmailServiceMS.event.TeacherCreateEvent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Method 1: Simple email for Teacher entity
    public void sendEmail(Teacher teacher) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("alihaider58162@gmail.com");
        message.setSubject("Welcome Teacher");
        message.setText("Hello " + teacher.getName() + ", your account is created!");

        try {
            mailSender.send(message);
            log.info("Email sent successfully to: {}", teacher.getEmail());
        } catch (Exception e) {
            log.error("Failed to send email to: {}", teacher.getEmail(), e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public void sendEmailByRabbitMQ(String email) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo("alihaider58162@gmail.com");
//        message.setSubject("Welcome Teacher");
//        message.setText("Hello Ali Haider, your account is created!");
//        try {
//            mailSender.send(message);
//            log.info("Email sent successfully to: alihaider58162@gmail.com");
//        } catch (Exception e) {
//            log.error("Failed to send email to: alihaider58162@gmail.com", e);
//            throw new RuntimeException("Failed to send email", e);
//        }
        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(email);

        message.setSubject("Welcome Teacher");

        message.setText(
                "Hello Ali Haider, your account is created!"
        );

        mailSender.send(message);
    }


    // Method 2: Kafka listener for TeacherCreateEvent
    @KafkaListener(topics = "teacher-created-topic" , groupId = "email-group")
    public void listen(TeacherCreateEvent teacherCreateEvent) {
        log.info("Received message from teacher-created-topic: {}", teacherCreateEvent);
        if (teacherCreateEvent == null) {
            log.error("Received null event");
            return;
        }
        if (teacherCreateEvent.getEmail() == null || teacherCreateEvent.getEmail().isEmpty()) {
            log.error("Email is null or empty in event: {}", teacherCreateEvent);
            return;
        }

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom("alihaider58162@gmail.com"); // Your Gmail
            messageHelper.setTo("alihaider58162@gmail.com");
            messageHelper.setSubject("Welcome Teacher - Account Created");
            messageHelper.setText(String.format("""
                Dear %s,
                
                Your teacher account has been successfully created.
                
                Teacher Name: %s
                Email: %s
                Subject: %s
                
                Best Regards,
                School Management System
                """,
                    teacherCreateEvent.getTeacherName(),
                    teacherCreateEvent.getTeacherName(),
                    teacherCreateEvent.getEmail(),
                    teacherCreateEvent.getSubject() != null ? teacherCreateEvent.getSubject() : "Not Specified"
            ));
        };

        try {
            mailSender.send(messagePreparator);
            log.info("Email sent successfully to: {}", teacherCreateEvent.getEmail());
        } catch (Exception e) {
            log.error("Exception occurred when sending mail to: {}", teacherCreateEvent.getEmail(), e);
            throw new RuntimeException("Exception occurred when sending mail", e);
        }
    }
}