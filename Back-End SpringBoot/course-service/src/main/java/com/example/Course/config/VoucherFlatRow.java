package com.example.Course.config;

import com.example.Course.Dto.CourseVoucherDto;
import com.example.Course.Dto.StudentVoucherDto;

// Yeh class sirf Jasper ke liye hai - temporary
public class VoucherFlatRow {
    private Long studentId;
    private String studentName;
    private String email;
    private String courseName;
    private Double coursePrice;
    private Double totalAmount;

    public VoucherFlatRow() {}

    public VoucherFlatRow(StudentVoucherDto dto, CourseVoucherDto course) {
        this.studentId = dto.getStudentId();
        this.studentName = dto.getStudentName();
        this.email = dto.getEmail();
        this.courseName = course.getCourseName();
        this.coursePrice = course.getCoursePrice();
        this.totalAmount = dto.getTotalAmount();
    }

    // GETTERS - MANDATORY for Jasper
    public Long getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getEmail() { return email; }
    public String getCourseName() { return courseName; }
    public Double getCoursePrice() { return coursePrice; }
    public Double getTotalAmount() { return totalAmount; }
}