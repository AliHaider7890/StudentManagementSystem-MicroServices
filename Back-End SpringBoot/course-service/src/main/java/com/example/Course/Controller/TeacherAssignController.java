package com.example.Course.Controller;


import com.example.Course.Entity.Course;
import com.example.Course.Entity.TeacherAssignCourse;
import com.example.Course.Repo.CourseRepo;
import com.example.Course.Service.CourseService;
import com.example.Course.Service.TeacherAssignCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courses")
public class TeacherAssignController {

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherAssignCourseService teacherAssignCourseService;

    @GetMapping("/getAllforTeachers")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/getTeacherAssignments")
    public List<TeacherAssignCourse> getTeacherAssignments() {
        return teacherAssignCourseService.getAllAssignments();
    }

    @PatchMapping("/assignCourseToTeacher/{currentTeacherAssign}/{courseId}/{teacherId}")
    public ResponseEntity<?> assignTeachertoCourses(@PathVariable Long currentTeacherAssign ,@PathVariable Long courseId , @PathVariable Long teacherId){
        teacherAssignCourseService.AssignCourseToTeacher(currentTeacherAssign , courseId , teacherId);
        return ResponseEntity.ok("Course " + courseId + "  has been assign to " + teacherId);
    }


    @GetMapping("/getTeacherById/{teacherId}")
    public Map<String, Object> getTeacherFromCourse(@PathVariable Long teacherId){
        return teacherAssignCourseService.getTeacherfromCourseService(teacherId);
    }

}
