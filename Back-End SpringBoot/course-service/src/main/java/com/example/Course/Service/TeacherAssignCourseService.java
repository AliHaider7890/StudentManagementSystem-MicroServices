package com.example.Course.Service;

import com.example.Course.Entity.Course;
import com.example.Course.Entity.TeacherAssignCourse;
import com.example.Course.Repo.CourseRepo;
import com.example.Course.Repo.TeacherAssignCourseRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherAssignCourseService {

    @Autowired
    private TeacherAssignCourseRepository assignRepo;
    @Autowired
    private CourseRepo courseRepo;
    private final TeacherClient teacherClient;

    @Autowired
    private CourseService courseService;

    public TeacherAssignCourseService(TeacherClient teacherClient) {
        this.teacherClient = teacherClient;
    }

    public List<TeacherAssignCourse> getAllAssignments() {
        return assignRepo.findAll();
    }

    public Map<String , Object> AssignCourseToTeacher(Long currentTeacherAssign ,Long courseId ,Long teacherId){

        TeacherAssignCourse teacherAssignCourse = assignRepo.findById(currentTeacherAssign).orElse(null);
        Map<String, Object> teacher = teacherClient.getTeacherById(teacherId);
        Course course = courseRepo.findById(courseId).orElse(null);
        Map<String , Object> response = new HashMap<>();
        response.put("Teacher" , teacher);
        // Courses Already he Fix bus TEACHER ASSign krne ki API he yeh Okay -
        teacherAssignCourse.setCourseName(course.getCourseName());
        teacherAssignCourse.setTeacherId(((Number) teacher.get("id")).longValue());
        teacherAssignCourse.setTeacherName(teacher.get("email").toString());
        assignRepo.save(teacherAssignCourse);
        return  response;

    }

    @CircuitBreaker(name = "teacherServiceCB", fallbackMethod = "getCourseWithTeacherFallback")
@Retry(name = "teacherServiceRetry")
public Map<String, Object> getTeacherfromCourseService(Long teacherId) {

//    long temp = 1;
//    Course course = courseRepo.findById(temp).orElse(null);
//    if(course == null) return null;

    Map<String, Object> teacher = teacherClient.getTeacherById(teacherId);

    Map<String, Object> response = new HashMap<>();
   // response.put("course", course);
    response.put("teacher", teacher);

    return response;
}

//    public Map<String, Object> getTeacher(Long teacherId) {
//
//       // Course course = courseRepo.findById(courseId).orElse(null);
//     //   if(course == null) return null;
//        //Long hardCode = 22;
//        Map<String, Object> teacher = teacherClient.getTeacherById(teacherId);
//
//        Map<String, Object> response = new HashMap<>();
//      //  response.put("course", course);
//        response.put("teacher", teacher);
//
//        return response;
//    }
//@CircuitBreaker(name = "teacherServiceCB", fallbackMethod = "getCourseWithTeacherFallback")
//@Retry(name = "teacherServiceRetry")
//public Map<String, Object> getCourseWithTeacher(Long courseId) {
//
//    long hardCode = 22;
//
//    Teacher teacher = teacherClient.getTeacherById(hardCode);
//
//    Map<String, Object> response = new HashMap<>();
//    response.put("teacher", teacher);
//
//    return response;
//}

}