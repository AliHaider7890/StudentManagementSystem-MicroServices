package com.example.Teacher.service;
import com.example.Teacher.entity.TimeTable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "course-service", url = "http://localhost:9090")
public interface TimeTableClient {
    @GetMapping("/timetable/GetMyTimeTable/{teacherId}")
    List<TimeTable> GetMyTimeTable(@PathVariable Long teacherId);
}