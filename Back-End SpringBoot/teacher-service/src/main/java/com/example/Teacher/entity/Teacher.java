//package com.example.Teacher.entity;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "teachers")
//public class Teacher {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//
//    private String subject;
//
//    private String email;
//
//    public Teacher() {}
//
//    public Teacher(Long id, String name, String subject, String email) {
//        this.id = id;
//        this.name = name;
//        this.subject = subject;
//        this.email = email;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getSubject() {
//        return subject;
//    }
//
//    public void setSubject(String subject) {
//        this.subject = subject;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//}

package com.example.Teacher.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "teachers")
@Schema(description = "Teacher Entity representing teacher details")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique ID of the teacher", example = "1")
    private Long id;

    @Schema(description = "Name of the teacher", example = "Ali Haider")
    private String name;

    @Schema(description = "Subject taught by teacher", example = "Mathematics")
    private String subject;

    @Schema(description = "Email of the teacher", example = "ali@example.com")
    private String email;

    public Teacher() {}

    public Teacher(Long id, String name, String subject, String email) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}