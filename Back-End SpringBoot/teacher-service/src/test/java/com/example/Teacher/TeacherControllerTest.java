package com.example.Teacher;

import com.example.Teacher.controller.TeacherController;
import com.example.Teacher.entity.Teacher;
import com.example.Teacher.service.TeacherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TeacherController.class)
@DisplayName("TeacherController Unit Tests")
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TeacherService service;

    @Autowired
    private ObjectMapper objectMapper;

    // ✅ CREATE
    @Test
    void createTeacher() throws Exception {

        Teacher teacher = new Teacher(1L, "Ali", "Math", "ali@temp.com");

        Mockito.when(service.createTeacher(any(Teacher.class))).thenReturn(teacher);

        mockMvc.perform(post("/teacher")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teacher)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ali"));
    }

    // ✅ GET ALL
    @Test
    void getAllTeachers() throws Exception {

        Mockito.when(service.getAllTeachers()).thenReturn(Arrays.asList(
                new Teacher(1L, "Ali", "Math", "ali@temp.com"),
                new Teacher(2L, "Sara", "Physics", "sara@temp.com")
        ));

        mockMvc.perform(get("/teacher"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    // ✅ GET BY ID (FOUND)
    @Test
    void getTeacherById() throws Exception {

        Mockito.when(service.getTeacherById(1L))
                .thenReturn(new Teacher(1L, "Ali", "Math", "ali@temp.com"));

        mockMvc.perform(get("/teacher/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ali"));
    }

    // ❌ GET BY ID (NOT FOUND)
    @Test
    void getTeacherNotFound() throws Exception {

        Mockito.when(service.getTeacherById(2L)).thenReturn(null);

        mockMvc.perform(get("/teacher/2"))
                .andExpect(status().isNotFound());
    }

    // ✅ UPDATE
    @Test
    void updateTeacher() throws Exception {

        Teacher updated = new Teacher(1L, "Ali Haider", "Physics", "alihaider@temp.com");

        Mockito.when(service.updateTeacher(Mockito.eq(1L), any(Teacher.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/teacher/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ali Haider"));
    }

    // ❌ UPDATE NOT FOUND
    @Test
    void updateTeacherNotFound() throws Exception {

        Mockito.when(service.updateTeacher(Mockito.eq(2L), any(Teacher.class)))
                .thenReturn(null);

        mockMvc.perform(put("/teacher/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Teacher())))
                .andExpect(status().isNotFound());
    }

    // ✅ DELETE
    @Test
    void deleteTeacher() throws Exception {

        Mockito.doNothing().when(service).deleteTeacher(1L);

        mockMvc.perform(delete("/teacher/1"))
                .andExpect(status().isOk());

        Mockito.verify(service, Mockito.times(1)).deleteTeacher(1L);
    }
}