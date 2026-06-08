package com.example.notification_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course implements Serializable {
    private Long id;
    private String courseName;
    private String description;
    private Long teacherId;
}