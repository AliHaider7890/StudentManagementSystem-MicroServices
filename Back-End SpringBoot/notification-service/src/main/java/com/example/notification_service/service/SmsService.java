////package com.example.notification_service.service;
////
////import com.twilio.rest.api.v2010.account.Message;
////import com.twilio.type.PhoneNumber;
////import lombok.extern.slf4j.Slf4j;
////import org.springframework.beans.factory.annotation.Value;
////import org.springframework.stereotype.Service;
////
////@Service
////@Slf4j
////public class SmsService {
////
////    @Value("${twilio.phone.number}")
////    private String fromPhoneNumber;
////
////    public void sendSms(String toPhoneNumber, String messageBody) {
////        try {
////            Message message = Message.creator(
////                    new PhoneNumber("+966596959826"),
////                    new PhoneNumber("+15005550006"),
////                    messageBody
////            ).create();
////
////            log.info("✅ SMS Sent!");
////            log.info("   To: {}", toPhoneNumber);
////            log.info("   Message SID: {}", message.getSid());
////
////        } catch (Exception e) {
////            log.error("❌ SMS Failed: {}", e.getMessage());
////        }
////    }
////}
//
//
//package com.example.notification_service.service;
//
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public class SmsService {
//
//    @Value("${twilio.phone.number}")
//    private String fromPhoneNumber;
//
//    public void sendSms(String toPhoneNumber, String messageBody) {
//        try {
//            // ✅ FIX: Use parameters, not hardcoded values
////            Message message = Message.creator(
////                    new PhoneNumber(toPhoneNumber),      // ← Use parameter
////                    new PhoneNumber(fromPhoneNumber),     // ← Use config value
////                    messageBody
////            ).create();
////
////            log.info("✅ SMS Sent!");
////            log.info("   To: {}", toPhoneNumber);
////            log.info("   From: {}", fromPhoneNumber);
////            log.info("   Message SID: {}", message.getSid());
//
//            Message message = Message.creator(
//                    new PhoneNumber("+15005550006"), // Test recipient (Twilio sandbox number)
//                    new PhoneNumber("+15005550006"), // Twilio sender/test number
//                    messageBody
//            ).create();
//
//            log.info("✅ SMS Sent in TEST mode!");
//            log.info("   To: {}", "+15005550006");
//            log.info("   Message SID: {}", message.getSid());
//
//        } catch (Exception e) {
//            log.error("❌ SMS Failed: {}", e.getMessage());
//        }
//    }
//}

package com.example.notification_service.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsService {

    // 🔥 Twilio test number (sandbox)
    private final String fromPhoneNumber = "+15005550006"; // Twilio test number

    public void sendSms(String toPhoneNumber, String messageBody) {
        try {
            // ✅ TEST mode: Both From & To are sandbox/test numbers
            Message message = Message.creator(
                    new PhoneNumber(fromPhoneNumber), // Test recipient
                    new PhoneNumber(fromPhoneNumber), // Sender (sandbox)
                    messageBody
            ).create();

            log.info("✅ SMS Sent in TEST mode!");
            log.info("   To: {}", toPhoneNumber);
            log.info("   From: {}", fromPhoneNumber);
            log.info("   Message SID: {}", message.getSid());
            log.info("   Message body (preview): {}", messageBody);

        } catch (Exception e) {
            log.error("❌ SMS Failed in TEST mode: {}", e.getMessage());
        }
    }
}