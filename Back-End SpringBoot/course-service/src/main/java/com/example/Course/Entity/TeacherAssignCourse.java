package com.example.Course.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "teacher_assign_courses")
public class TeacherAssignCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;           // 👈 1. Unique ID

    @Column(name = "teacher_name")
    private String teacherName;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_id")
    private Long courseId;     // 👈 2. Course ID

    @Column(name = "teacher_id")
    private Long teacherId;    // 👈 3. Teacher ID

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public void setTeacherName(String teacherName){ this.teacherName = teacherName; }

    public String getTeacherName() {
        return teacherName;
    }

    public void setCourseName(String courseName){   this.courseName = courseName; }

    public String getCourseName() {
        return courseName;
    }



}