package com.example.Course.Controller;

import com.example.Course.Entity.TimeTable;
import com.example.Course.Enum.Day;
import com.example.Course.Service.TimeTableService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/timetable")
public class TimeTableController {

    private final TimeTableService service;


    public TimeTableController(TimeTableService service) {
        this.service = service;
    }

    @PatchMapping("/assignTimeTable/{courseXteacher}/{timeTableId}")
    public TimeTable assignTimeTable(@PathVariable Long courseXteacher ,@PathVariable Long timeTableId){
        return service.assignCourseXTeacher(courseXteacher , timeTableId );
    }

    @PostMapping
    public TimeTable add(@RequestBody TimeTable entry) {
        return service.addEntry(entry);
    }

    @GetMapping("/getTimeTable")
    public List<TimeTable> getAll() {
        return service.getAll();
    }

    @GetMapping("/day/{day}")
    public List<TimeTable> getByDay(@PathVariable Day day) {
        return service.getByDay(day);
    }

//    @GetMapping("/teacher/{teacherId}")
//    public List<TimeTable> getByTeacher(@PathVariable Long teacherId) {
//        return service.getByTeacher(teacherId);
//    }
//
//    @GetMapping("/course/{courseId}")
//    public List<TimeTable> getByCourse(@PathVariable Long courseId) {
//        return service.getByCourse(courseId);
//    }
    @GetMapping("/GetMyTimeTable/{teacherId}")
    public List<TimeTable> GetMyTimeTable(@PathVariable Long teacherId){
        return service.GetMyTimeTable(teacherId);
       // /timetable/GetMyTimeTable/{teacherId}
    }
}