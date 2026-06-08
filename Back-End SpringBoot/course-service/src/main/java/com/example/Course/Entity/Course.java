package com.example.Course.Entity;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Course_Micro")
public class Course  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseName;
    private String description;
    private Long teacherId;
    private int courseQuantity;
    private Double coursePrice;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students = new ArrayList<>();
    public Course() {}

    public Course(Long id, String courseName, String description, Long teacherId , Integer courseQuantity , Double coursePrice) {
        this.id = id;
        this.courseName = courseName;
        this.description = description;
        this.teacherId = teacherId;
        this.courseQuantity = courseQuantity;
        this.coursePrice = coursePrice;
    }

    public Long getId() {
        return id;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public String getDescription() {
        return description;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

//    public String getCourseName(){
//        return  courseName;
//    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public void setCourseQuantity(Integer courseQuantity){
        this.courseQuantity = courseQuantity;
    }
    public int getCourseQuantity(){
        return this.courseQuantity;
    }

    public void setCoursePrice(Double coursePrice){
        this.coursePrice = coursePrice;
    }
    public Double getCoursePrice()
    {
        return this.coursePrice;
    }

}