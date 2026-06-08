package com.example.Course.Repo;

import com.example.Course.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
    @Modifying
    @Query("UPDATE Course c SET c.courseQuantity = c.courseQuantity - 1 WHERE c.id = :courseId AND c.courseQuantity > 0")
    int decrementSeat(@Param("courseId") Long courseId);
}