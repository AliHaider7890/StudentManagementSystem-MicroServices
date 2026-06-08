package com.example.Course.Service;
import com.example.Course.Entity.Course;
import com.example.Course.Repo.CourseRepo;
import com.example.Course.Repo.TeacherAssignCourseRepository;
import com.example.Course.config.RabbitMQConfig;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CourseService {

    private final CourseRepo courseRepo;
    private final TeacherClient teacherClient;
    private final RabbitTemplate rabbitTemplate;
    private final TeacherAssignCourseRepository teacherAssignCourseRepository;

    public CourseService(CourseRepo courseRepo, TeacherClient teacherClient, RabbitTemplate rabbitTemplate, TeacherAssignCourseRepository teacherAssignCourseRepository) {
        this.courseRepo = courseRepo;
        this.teacherClient = teacherClient;
        this.rabbitTemplate = rabbitTemplate;
        this.teacherAssignCourseRepository = teacherAssignCourseRepository;
    //    this.te
    }

//    public Course addCourse(Course course) {
//        return courseRepo.save(course);
//    }

    public Course addCourse(Course course) {
        // 1. Save to database
        Course savedCourse = courseRepo.save(course);
        log.info("✅ Course saved to DB: {}", savedCourse.getCourseName());

        // 2. Send message to RabbitMQ for Notification Service
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.NOTIFICATION_EXCHANGE,
                    RabbitMQConfig.NOTIFICATION_ROUTING_KEY,
                    savedCourse
            );
            log.info("📨 RabbitMQ message sent for course: {}", savedCourse.getCourseName());
        } catch (Exception e) {
            log.error("❌ Failed to send RabbitMQ message: {}", e.getMessage());
        }

        return savedCourse;
    }


//    @RateLimiter(name = "courseLimiter")
//    public List<Course>getAllCourses() {
////        List<Course>  courses  = courseRepo.findAll();
////        for (Course course : courses) {
////            TeacherAssignCourse assign = new TeacherAssignCourse();
////            assign.setCourseId(course.getId());  // Course ki ID le li
////            // assign.setTeacherId(???); // Teacher ID bhi chahiye to daal do
////            teacherAssignCourseRepository.save(assign);  // Doosri repo mein daal di
////        }
//        return courseRepo.findAll();
//    }

@RateLimiter(
        name = "courseLimiter",
        fallbackMethod = "rateLimitFallback"
)
public List<Course> getAllCourses() {

    return courseRepo.findAll();
}

    public List<Course> rateLimitFallback(RequestNotPermitted ex) {

        throw new RuntimeException("Too many requests");
    }

    public Course getCourseById(Long id) {
        return courseRepo.findById(id).orElse(null);
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        return courseRepo.findById(id).map(course -> {
            course.setCourseName(updatedCourse.getCourseName());
            course.setDescription(updatedCourse.getDescription());
            course.setTeacherId(updatedCourse.getTeacherId());
            return courseRepo.save(course);
        }).orElse(null);
    }

    public boolean deleteCourse(Long id) {
        if(courseRepo.existsById(id)){
            courseRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @CircuitBreaker(name = "teacherServiceCB", fallbackMethod = "getCourseWithTeacherFallback")
    @Retry(name = "teacherServiceRetry")
    public Map<String, Object> getCourseWithTeacher(Long courseId) {

        long temp = 1;
        Course course = courseRepo.findById(temp).orElse(null);
        if(course == null) return null;
        Map<String, Object> teacher = teacherClient.getTeacherById(course.getTeacherId());
        Map<String, Object> response = new HashMap<>();
        response.put("course", course);
        response.put("teacher", teacher);
        return response;

    }
//@CircuitBreaker(name = "teacherServiceCB", fallbackMethod = "getCourseWithTeacherFallback")
//@Retry(name = "teacherServiceRetry")
//public Map<String, Object> getTeacherfromCourseService(Long teacherId) {
//
////    long temp = 1;
////    Course course = courseRepo.findById(temp).orElse(null);
////    if(course == null) return null;
//
//    Map<String, Object> teacher = teacherClient.getTeacherById(teacherId);
//
//    Map<String, Object> response = new HashMap<>();
//   // response.put("course", course);
//    response.put("teacher", teacher);
//
//    return response;
//}

    // Add this method at the end of CourseService class
    private Map<String, Object> getCourseWithTeacherFallback(Long courseId, Throwable t) {
        log.error("Fallback triggered for course: {}", courseId, t);

        Course course = courseRepo.findById(courseId).orElse(null);

        Map<String, Object> response = new HashMap<>();

        if(course != null) {
            response.put("course", course);
        } else {
            response.put("error", "Course not found");
        }

        Map<String, Object> fallbackTeacher = new HashMap<>();
        fallbackTeacher.put("id", -1L);
        fallbackTeacher.put("name", "Teacher Service Unavailable");
        fallbackTeacher.put("email", "fallback@system.com");
        fallbackTeacher.put("message", "Teacher service is temporarily down");
        response.put("teacher", fallbackTeacher);
        response.put("fallback", true);

        return response;
    }

}