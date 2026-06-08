package com.example.Course.Dto;

import java.util.List;

public class StudentVoucherDto {

    private Long studentId;
    private String studentName;
    private String email;

    private List<CourseVoucherDto> courses;

    private Double totalAmount;

    public StudentVoucherDto() {
    }

    public StudentVoucherDto(Long studentId,
                             String studentName,
                             String email,
                             List<CourseVoucherDto> courses,
                             Double totalAmount) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.courses = courses;
        this.totalAmount = totalAmount;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CourseVoucherDto> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseVoucherDto> courses) {
        this.courses = courses;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}