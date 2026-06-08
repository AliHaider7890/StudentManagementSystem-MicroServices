package com.example.Teacher.service;

import com.example.Teacher.Repository.TeacherRepo;
import com.example.Teacher.entity.Teacher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TeacherService Unit Tests with Mockito")
class TeacherServiceTest {

    @Mock
    private TeacherRepo repo;

    @InjectMocks
    private TeacherService service;

    @Nested
    @DisplayName("Create Teacher Tests")
    class CreateTeacherTests {

        @Test
        @DisplayName("Create Teacher - Success")
        void createTeacherSuccess() {
            Teacher teacher = new Teacher(null, "Ali", "Math", "ali@temp.com");
            when(repo.save(any(Teacher.class))).thenReturn(new Teacher(1L, "Ali", "Math", "ali@temp.com"));

            Teacher created = service.createTeacher(teacher);

            assertNotNull(created);
            assertEquals(1L, created.getId());
            assertEquals("Ali", created.getName());
            verify(repo, times(1)).save(teacher);
        }

        @Test
        @DisplayName("Create Teacher - Fail Example")
        void createTeacherFail() {
            Teacher teacher = new Teacher(null, "Ali", "Math", "ali@temp.com");
            when(repo.save(any(Teacher.class))).thenReturn(new Teacher(1L, "Ali", "Math", "ali@temp.com"));

            Teacher created = service.createTeacher(teacher);

            // Intentionally wrong assertion to show fail
            assertNotEquals("WrongName", created.getName());
        }
    }

    @Nested
    @DisplayName("Get Teacher Tests")
    class GetTeacherTests {

        @Test
        @DisplayName("Get Teacher By ID - Found")
        void getTeacherByIdFound() {
            Teacher teacher = new Teacher(1L, "Ali", "Math", "ali@temp.com");
            when(repo.findById(1L)).thenReturn(Optional.of(teacher));

            Teacher result = service.getTeacherById(1L);

            assertNotNull(result);
            assertEquals("Ali", result.getName());
            verify(repo, times(1)).findById(1L);
        }

        @Test
        @DisplayName("Get Teacher By ID - Not Found")
        void getTeacherByIdNotFound() {
            when(repo.findById(2L)).thenReturn(Optional.empty());

            Teacher result = service.getTeacherById(2L);

            assertNull(result);
            verify(repo, times(1)).findById(2L);
        }
    }

    @Nested
    @DisplayName("Update Teacher Tests")
    class UpdateTeacherTests {

        @Test
        @DisplayName("Update Teacher - Success")
        void updateTeacherSuccess() {
            Teacher existing = new Teacher(1L, "Ali", "Math", "ali@temp.com");
            Teacher update = new Teacher(null, "Ali Haider", "Physics", "alihaider@temp.com");

            when(repo.findById(1L)).thenReturn(Optional.of(existing));
            when(repo.save(any(Teacher.class))).thenReturn(new Teacher(1L, "Ali Haider", "Physics", "alihaider@temp.com"));

            Teacher updated = service.updateTeacher(1L, update);

            assertNotNull(updated);
            assertEquals("Ali Haider", updated.getName());
            verify(repo, times(1)).findById(1L);
            verify(repo, times(1)).save(existing);
        }

        @Test
        @DisplayName("Update Teacher - Not Found")
        void updateTeacherNotFound() {
            Teacher update = new Teacher(null, "Ali Haider", "Physics", "alihaider@temp.com");
            when(repo.findById(2L)).thenReturn(Optional.empty());

            Teacher updated = service.updateTeacher(2L, update);

            assertNull(updated);
            verify(repo, times(1)).findById(2L);
            verify(repo, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Delete Teacher Tests")
    class DeleteTeacherTests {

        @Test
        @DisplayName("Delete Teacher - Verify Call")
        void deleteTeacher() {
            doNothing().when(repo).deleteById(1L);

            service.deleteTeacher(1L);

            verify(repo, times(1)).deleteById(1L);
        }
    }

    @Nested
    @DisplayName("Get All Teachers Tests")
    class GetAllTeachersTests {

        @Test
        @DisplayName("Get All Teachers - Success")
        void getAllTeachers() {
            List<Teacher> teachers = Arrays.asList(
                    new Teacher(1L, "Ali", "Math", "ali@temp.com"),
                    new Teacher(2L, "Sara", "Physics", "sara@temp.com")
            );
            when(repo.findAll()).thenReturn(teachers);

            List<Teacher> result = service.getAllTeachers();

            assertEquals(2, result.size());
            verify(repo, times(1)).findAll();
        }
    }
}