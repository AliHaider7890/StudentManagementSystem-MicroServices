package com.example.Course.Event;

public class StudentCreationFailedEvent
{

    private Long userId;
    private String email;
   // private String reason;

    public StudentCreationFailedEvent() {
    }

    public StudentCreationFailedEvent(
            Long userId,
            String email,
            String reason
    ) {
        this.userId = userId;
        this.email = email;
       // this.reason = reason;
    }

    public StudentCreationFailedEvent(Long id, String email) {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getReason() {
//        return reason;
//    }
//
//    public void setReason(String reason) {
//        this.reason = reason;
//    }
}