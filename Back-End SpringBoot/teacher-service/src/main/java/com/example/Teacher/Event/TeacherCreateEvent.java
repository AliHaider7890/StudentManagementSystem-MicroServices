package com.example.Teacher.Event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherCreateEvent {

    private String teacherName;
    private String email;

    public TeacherCreateEvent(Long id, String email) {
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void SetTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}