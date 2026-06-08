package com.example.Course.Dto;

public class CourseVoucherDto {

    private String courseName;
    private Double coursePrice;

    public CourseVoucherDto() {
    }

    public CourseVoucherDto(String courseName, Double coursePrice) {
        this.courseName = courseName;
        this.coursePrice = coursePrice;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(Double coursePrice) {
        this.coursePrice = coursePrice;
    }
}