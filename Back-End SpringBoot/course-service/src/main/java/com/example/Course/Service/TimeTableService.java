package com.example.Course.Service;
import com.example.Course.Entity.TeacherAssignCourse;
import com.example.Course.Entity.TimeTable;
import com.example.Course.Enum.Day;
import com.example.Course.Repo.TeacherAssignCourseRepository;
import com.example.Course.Repo.TimeTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TimeTableService {

    private final TimeTableRepository repo;

    @Autowired
    private TeacherAssignCourseRepository teacherAssignCourseRepository;

    public TimeTableService(TimeTableRepository repo) {
        this.repo = repo;
    }

    public TimeTable addEntry(TimeTable entry) {
        return repo.save(entry);
    }

    public List<TimeTable> getAll() {
        return repo.findAll();
    }

    public List<TimeTable> getByDay(Day day) {
        return repo.findByDay(day);
    }

    public TimeTable assignCourseXTeacher(Long courseXteacher , Long timeTableId){
        TeacherAssignCourse teacherAssignCourse = teacherAssignCourseRepository.findById(courseXteacher).orElse(null);
        TimeTable timeTable = repo.findById(timeTableId).orElse(null);
        timeTable.setTeacherAssignCourse(teacherAssignCourse);
        repo.save(timeTable);
        return timeTable;
    }

    public List<TimeTable> GetMyTimeTable(Long teacherId){
        List<TimeTable>  timeTable = repo.findByTeacherId(teacherId);
        return timeTable;
    }

}