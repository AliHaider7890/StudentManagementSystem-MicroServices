package com.example.Teacher.service;
import com.example.Teacher.Event.TeacherCreateEvent;
import com.example.Teacher.Repository.TeacherRepo;
import com.example.Teacher.entity.Teacher;
import com.example.Teacher.entity.TimeTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class TeacherService {

    @Autowired
    private TeacherRepo repo;

    private final TimeTableClient timeTableClient;
    private final KafkaTemplate<Object, String> kafkaTemplate;

    public TeacherService(TimeTableClient timeTableClient,
                          KafkaTemplate<Object, String> kafkaTemplate) {
        this.timeTableClient = timeTableClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Teacher createTeacher(Teacher teacher) {
        // 1. Save in DB
        Teacher saved = repo.save(teacher);

        // 2. Create event with actual teacher data
        TeacherCreateEvent teacherCreateEvent = new TeacherCreateEvent(
                saved.getName(),
               // saved.getName(),
                "alihaider58162@gmail.com"    // Use saved teacher's email, not hardcoded
              //  saved.getSubject()
        );

        // 3. Send to Kafka
       kafkaTemplate.send("teacher-created-topic", teacherCreateEvent.toString());

        return saved;
    }


    public List<Teacher> getAllTeachers() {
        return repo.findAll();
    }

    public Teacher getTeacherById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Teacher updateTeacher(Long id, Teacher teacher) {
        Teacher t = repo.findById(id).orElse(null);

        if (t != null) {
            t.setName(teacher.getName());
            t.setSubject(teacher.getSubject());
            t.setEmail(teacher.getEmail());
            return repo.save(t);
        }
        return null;
    }

    public void deleteTeacher(Long id) {
        repo.deleteById(id);
    }

    public List<TimeTable> getMyTimeTable(Long teacherId){
//        long temp = 1;
//        Course course = courseRepo.findById(temp).orElse(null);
//        if(course == null) return null;
//        Map<String, Object> teacher = teacherClient.getTeacherById(course.getTeacherId());
//        Map<String, Object> response = new HashMap<>();
//        response.put("course", course);
//        response.put("teacher", teacher);
    //    return response;
        List<TimeTable> timeTables = timeTableClient.GetMyTimeTable(teacherId);
        return timeTables;
    }
}