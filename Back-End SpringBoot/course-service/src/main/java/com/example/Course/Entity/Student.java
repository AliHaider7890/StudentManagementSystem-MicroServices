package com.example.Course.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "student_micro_services")
public class Student {

//    @Id
//   // @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;


    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "Finalized_Course")
    private Boolean isFinalized;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "age")
    private int age;

    // Default Constructor
    public Student() {
    }

    private int assignCourseId;

    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses = new ArrayList<>();

    // Parameterized Constructor
    public Student(String name, String email, int age, int assignCourseId) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.assignCourseId = assignCourseId;
    }


    public void setassignCourses(int assignCourseId) {
        this.assignCourseId = assignCourseId;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public int getAssignCourseId() {
        return this.assignCourseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Boolean getIsFinalized() {
        return isFinalized;
    }

    public void setIsFinalized(Boolean isFinalized) {
        this.isFinalized = isFinalized;
    }

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}