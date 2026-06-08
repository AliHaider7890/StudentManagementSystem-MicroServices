package com.example.Course.Service;

import com.example.Course.config.TeacherServiceClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;


@FeignClient(name = "teacher-service", url = "http://localhost:9091"
,
        fallback = TeacherServiceClientFallback.class) // teacher-service port
public interface TeacherClient {

    @GetMapping("/teacher/{id}")   // exact path jaisa TeacherController me hai
    Map<String, Object> getTeacherById(@PathVariable("id") Long id);

    //Teacher getTeacherByCourseId(Long courseId);
}