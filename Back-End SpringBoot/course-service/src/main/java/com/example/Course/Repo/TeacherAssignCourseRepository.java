package com.example.Course.Repo;

import com.example.Course.Entity.Course;

import com.example.Course.Entity.Student;
import com.example.Course.Entity.TeacherAssignCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherAssignCourseRepository extends JpaRepository <TeacherAssignCourse, Long> {
//    List<TeacherAssignCourse> findByTeacher(Teacher teacher);
//
//    // Find by teacher ID
//    List<TeacherAssignCourse> findByTeacherId(Long teacherId);
//
//    // Find by course
//    List<TeacherAssignCourse> findByCourse(Course course);
//
//    // Find by course ID
//    List<TeacherAssignCourse> findByCourseId(Long courseId);
//
//    // Check if already assigned
//    boolean existsByCourseIdAndTeacherId(Long courseId, Long teacherId);
//
//    // Delete assignment
//    void deleteByCourseIdAndTeacherId(Long courseId, Long teacherId);
}
