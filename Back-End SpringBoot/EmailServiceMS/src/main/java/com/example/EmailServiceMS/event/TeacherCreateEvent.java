package com.example.EmailServiceMS.event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherCreateEvent {
    private Long id;
    private String teacherName;
    private String email;
    private String subject;
}