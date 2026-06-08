package com.example.Course.Entity;
import com.example.Course.Enum.Day;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "timetable")
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Day day;

    private LocalTime startTime;
    private LocalTime endTime;
    private String roomNo;

    // ✅ Many-to-One: Ek TeacherAssignCourse ki multiple timetable entries ho sakti hain
    @ManyToOne
    @JoinColumn(name = "teacher_assign_course_id")
    private TeacherAssignCourse teacherAssignCourse;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Day getDay() { return day; }
    public void setDay(Day day) { this.day = day; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public String getRoomNo() { return roomNo; }
    public void setRoomNo(String roomNo) { this.roomNo = roomNo; }

    public TeacherAssignCourse getTeacherAssignCourse() { return teacherAssignCourse; }
    public void setTeacherAssignCourse(TeacherAssignCourse teacherAssignCourse) {
        this.teacherAssignCourse = teacherAssignCourse;
    }

    // Helper methods
    public Long getTeacherId() {
        return teacherAssignCourse != null ? teacherAssignCourse.getTeacherId() : null;
    }

//    public Long getCourseId() {
//        return teacherAssignCourse != null ? teacherAssignCourse.getCourseId() : null;
//    }
}