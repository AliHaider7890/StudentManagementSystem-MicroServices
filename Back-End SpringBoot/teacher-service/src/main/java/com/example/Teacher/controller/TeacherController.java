
package com.example.Teacher.controller;

import com.example.Teacher.entity.Teacher;
import com.example.Teacher.entity.TimeTable;
import com.example.Teacher.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.*;
import jakarta.ws.rs.Path;
import org.apache.kafka.common.security.auth.AuthenticationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@Tag(name = "Teacher APIs", description = "Operations related to Teacher management")
public class TeacherController {

    @Autowired
    private TeacherService service;

    //Swagger Implementation
    @Operation(summary = "Create a new teacher", description = "Add a new teacher to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public Teacher createTeacher(@RequestBody Teacher teacher){
        return service.createTeacher(teacher);
    }


    @GetMapping("/getAssignCourses")
    public List<?> GetMyCourses(){

        return null;
    }

    @Operation(summary = "Get all teachers", description = "Fetch all teachers from database")
    @ApiResponse(responseCode = "200", description = "List of teachers retrieved successfully")
    @GetMapping
    public List<Teacher> getAllTeachers(){
        AuthenticationContext context;
        return service.getAllTeachers();
    }

    @Operation(summary = "Get teacher by ID", description = "Fetch a teacher using ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher found"),
            @ApiResponse(responseCode = "404", description = "Teacher not found")
    })
    @GetMapping("/{id}")
    public Teacher getTeacher(@PathVariable Long id){
        return service.getTeacherById(id);
    }

    @Operation(summary = "Update teacher", description = "Update teacher details by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher updated successfully"),
            @ApiResponse(responseCode = "404", description = "Teacher not found")
    })
    @PutMapping("/{id}")
    public Teacher updateTeacher(@PathVariable Long id,
                                 @RequestBody Teacher teacher){
        return service.updateTeacher(id, teacher);
    }

    @Operation(summary = "Delete teacher", description = "Delete a teacher by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Teacher not found")
    })
    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        service.deleteTeacher(id);
    }

    @GetMapping("/mytimetable/{teacherId}")
    public List<TimeTable> getMyTimeTable(@PathVariable Long teacherId){
        return service.getMyTimeTable(teacherId);
    }
}