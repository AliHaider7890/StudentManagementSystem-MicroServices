package com.example.Course.config;

import com.example.Course.Service.TeacherClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class TeacherServiceClientFallback implements TeacherClient {

    @Override
    public Map<String, Object> getTeacherById(Long id) {
        log.warn("Feign Client Fallback triggered for teacher ID: {}", id);

        Map<String, Object> fallbackTeacher = new HashMap<>();
        fallbackTeacher.put("id", id);
        fallbackTeacher.put("name", "Teacher Service Unavailable");
        fallbackTeacher.put("email", "fallback@system.com");
        fallbackTeacher.put("error", "Teacher service is temporarily down");
        fallbackTeacher.put("available", false);

        return fallbackTeacher;
    }
}