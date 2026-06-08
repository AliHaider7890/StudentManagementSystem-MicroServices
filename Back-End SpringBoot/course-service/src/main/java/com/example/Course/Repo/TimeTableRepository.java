package com.example.Course.Repo;

import com.example.Course.Entity.TimeTable;
import com.example.Course.Enum.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {
    List<TimeTable> findByDay(Day day);                    // Enum ke saath
  //  List<TimeTable> findByTeacherId(Long teacherId);
    @Query("""
SELECT t FROM TimeTable t
WHERE t.teacherAssignCourse.teacherId = :teacherId
""")
    List<TimeTable> findByTeacherId(@Param("teacherId") Long teacherId);
//    List<TimeTable> findByCourseId(Long courseId);
 //   List<TimeTable> findByDayAndTeacherId(Day day, Long teacherId);
}